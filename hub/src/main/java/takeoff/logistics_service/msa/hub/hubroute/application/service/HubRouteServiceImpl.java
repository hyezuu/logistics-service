package takeoff.logistics_service.msa.hub.hubroute.application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.FindHubRoutesDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubAllListResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubRoutesDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.HubIdsDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostDeliveryHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PutHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteNaverResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PostHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PutHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteBusinessException;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteErrorCode;
import takeoff.logistics_service.msa.hub.hubroute.application.service.client.HubClient;
import takeoff.logistics_service.msa.hub.hubroute.application.service.client.NaverRequestClient;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;
import takeoff.logistics_service.msa.hub.hubroute.domain.repository.HubRouteRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HubRouteServiceImpl implements HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubClient hubClient;
    private final NaverRequestClient naverRequestClient;

    private static final int EARTH_RADIUS_KM = 6371;

    @Override
    public PostHubRouteResponseDto createHubRoute(PostHubRouteRequestDto requestDto) {

        List<GetRouteResponseDto> responseToHub = hubClient.findByToHubIdAndFromHubId(
            HubIdsDto.from(requestDto));

        GetHubRouteNaverResponseDto result = naverRequestClient.sendRequestToNaver(responseToHub)
            .onErrorMap(error -> {
                log.error("NaverAPI 응답을 받을 수 없습니다.", error);
                return HubRouteBusinessException.from(HubRouteErrorCode.NAVER_ERROR);
            })
            .block();


        HubRoute hubRoute = result.toEntity(result, requestDto);
        HubRoute savedHubRoute = hubRouteRepository.save(hubRoute);

        return PostHubRouteResponseDto.from(savedHubRoute);

    }

    //P2P, Hub To Hub Relay 구현
    //데이터베이스에 경로가 없어도 경로를 구해서 저장하게끔 구현
    @Override
    public HubRoutesDto getDeliveryHubRouteList(PostDeliveryHubRouteRequestDto request) {
        List<HubAllListResponseDto> allHubs = hubClient.findAllHubs();

        // 결과 저장할 HubRoute가 담긴 List
        List<FindHubRoutesDto> hubRoutesList = new ArrayList<>();

        HubAllListResponseDto fromHub = allHubs.stream()
            .filter(hub -> hub.hubId().equals(request.fromHubId()))
            .findFirst()
            .orElseThrow(() -> HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND));

        HubAllListResponseDto toHub = allHubs.stream()
            .filter(hub -> hub.hubId().equals(request.toHubId()))
            .findFirst()
            .orElseThrow(() -> HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND));

        log.info("시작 허브: {}", fromHub.hubName());
        log.info("끝 허브: {}", toHub.hubName());

        // 허브 간 거리 계산
        double distance = calculateDistance(
            fromHub.latitude(), fromHub.longitude(),
            toHub.latitude(), toHub.longitude()
        );

        if (distance <= 200) {
            // P2P 200km 이하일 경우
            log.info("200km 이하 입니다.");
            Optional<HubRoute> byFromHubIdAndToHubId = hubRouteRepository.findByFromHubIdAndToHubId(
                fromHub.hubId(), toHub.hubId());
            if (byFromHubIdAndToHubId.isEmpty()) {
                // 저장된 경로가 없는 경우
                log.info("200km 이하이며 경로가 없습니다.");
                List<GetRouteResponseDto> createHubRoute = Stream.of(fromHub, toHub)
                    .map(hub -> new GetRouteResponseDto(hub.hubId(), hub.hubName(), hub.address(),
                        hub.latitude(), hub.longitude()))
                    .toList();
                HubRoute hubRoute = getHubRouteFromNaver(createHubRoute, fromHub, toHub);
                HubRoute savedRoute = hubRouteRepository.save(hubRoute);
                hubRoutesList.add(FindHubRoutesDto.from(savedRoute));
            } else {
                // 저장된 경로가 있는 경우
                log.info("200km 이하이며 경로가 있습니다.");
                hubRoutesList.add(FindHubRoutesDto.from(byFromHubIdAndToHubId
                    .orElseThrow(() -> HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND))));
            }
        } else {
            // 200km이 넘을 경우
            log.info("200km 이상입니다.");
            HubAllListResponseDto stopoverHub = findClosestHubWithinDistance(fromHub, toHub, allHubs);

            // 시작점 -> 중간 지점까지 저장된 경로가 있는지 확인
            Optional<HubRoute> byFromHubIdAndStopoverHubId = hubRouteRepository.findByFromHubIdAndToHubId(
                fromHub.hubId(), stopoverHub.hubId());
            if (byFromHubIdAndStopoverHubId.isEmpty()) {
                // 저장된 경로가 없을 때 네이버 API를 통해 계산 후 저장
                log.info("200km 이상이며 초반 경로가 없습니다.");
                List<GetRouteResponseDto> createHubRoute = Stream.of(fromHub, stopoverHub)
                    .map(hub -> new GetRouteResponseDto(hub.hubId(), hub.hubName(), hub.address(),
                        hub.latitude(), hub.longitude()))
                    .toList();
                HubRoute hubRoute = getHubRouteFromNaver(createHubRoute, fromHub, stopoverHub);
                HubRoute savedRoute = hubRouteRepository.save(hubRoute);
                hubRoutesList.add(FindHubRoutesDto.from(savedRoute));
            } else {
                // 저장된 경로가 있는 경우
                log.info("200km 이상이며 초반 경로가 있습니다.");
                hubRoutesList.add(FindHubRoutesDto.from(byFromHubIdAndStopoverHubId
                    .orElseThrow(() -> HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND))));
            }

            // 중간 지점 -> 도착 지점까지 경로가 있는지 확인
            Optional<HubRoute> byStopoverHubIdAndToHubId = hubRouteRepository.findByFromHubIdAndToHubId(
                stopoverHub.hubId(), toHub.hubId());
            if (byStopoverHubIdAndToHubId.isEmpty()) {
                // 저장된 경로가 없을 때 네이버 API를 통해 계산 후 저장
                log.info("200km 이상이며 후반 경로가 없습니다.");
                List<GetRouteResponseDto> createHubRoute = Stream.of(stopoverHub, toHub)
                    .map(hub -> new GetRouteResponseDto(hub.hubId(), hub.hubName(), hub.address(),
                        hub.latitude(), hub.longitude()))
                    .toList();
                HubRoute hubRoute = getHubRouteFromNaver(createHubRoute, stopoverHub, toHub);
                HubRoute savedRoute = hubRouteRepository.save(hubRoute);
                hubRoutesList.add(FindHubRoutesDto.from(savedRoute));
            } else {
                // 저장된 경로가 있는 경우
                log.info("200km 이상이며 후반 경로가 있습니다.");
                hubRoutesList.add(FindHubRoutesDto.from(byStopoverHubIdAndToHubId
                    .orElseThrow(() -> HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND))));
            }
        }

        return new HubRoutesDto(hubRoutesList);
    }


    private HubRoute getHubRouteFromNaver(List<GetRouteResponseDto> createHubRoute,
        HubAllListResponseDto fromHub, HubAllListResponseDto toHub) {
        GetHubRouteNaverResponseDto result = naverRequestClient.sendRequestToNaver(
                createHubRoute)
            .onErrorMap(error -> {
                log.error("NaverAPI 응답을 받을 수 없습니다.", error);
                return HubRouteBusinessException.from(HubRouteErrorCode.NAVER_ERROR);
            })
            .block();

        HubRoute hubRoute = GetHubRouteNaverResponseDto
            .toEntity(result,
                PostHubRouteRequestDto
                    .createDto(fromHub.hubId(), toHub.hubId()));
        FindHubRoutesDto.from(hubRouteRepository.save(hubRoute));
        return hubRoute;
    }

    //목적지에서 가장 가까운 허브 찾기
    private HubAllListResponseDto findClosestHubWithinDistance(
        HubAllListResponseDto fromHub,
        HubAllListResponseDto toHub,
        List<HubAllListResponseDto> hubs) {

        // 출발 허브에서 목적지 허브까지의 거리 계산
        double directDistance = calculateDistance(fromHub.latitude(), fromHub.longitude(),
            toHub.latitude(), toHub.longitude());

        return hubs.stream()
            .filter(hub -> verifyHub(fromHub, toHub, hub))
            .filter(hub -> calculateDistance(fromHub.latitude(), fromHub.longitude(), hub.latitude(), hub.longitude()) <= 200)
            .filter(hub -> {
                // 출발 허브에서 중간 허브까지의 거리와 출발 허브에서 목적지 허브까지의 거리를 비교
                double distanceToHub = calculateDistance(fromHub.latitude(), fromHub.longitude(), hub.latitude(), hub.longitude());
                return distanceToHub <= directDistance;
            })
            .min(Comparator.comparingDouble(hub ->
                calculateDistance(toHub.latitude(), toHub.longitude(), hub.latitude(), hub.longitude())
            ))
            .orElseThrow(() -> HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND));
    }

    private static boolean verifyHub(HubAllListResponseDto fromHub, HubAllListResponseDto toHub,
        HubAllListResponseDto hub) {
        return !hub.hubId().equals(fromHub.hubId()) && !hub.hubId().equals(toHub.hubId());
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "hubRoutes", key = "#hubRouteId")
    public GetHubRouteResponseDto findByHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = getHubRoute(
            hubRouteId);
        return GetHubRouteResponseDto.from(hubRoute);
    }

    @Override
    @CacheEvict(value = "hubRoutes", allEntries = true)
    public PutHubRouteResponseDto updateHubRoute(UUID hubRouteId,
        PutHubRouteRequestDto requestDto) {
        HubRoute hubRoute = getHubRoute(
            hubRouteId);
        hubRoute.modifyHubRoute(
            requestDto.fromHubId(),
            requestDto.toHubId(),
            requestDto.distance().getDistanceKm(),
            requestDto.duration().getDurationMinutes());
        return PutHubRouteResponseDto.from(hubRoute);
    }


    @Override
    @CacheEvict(value = "hubRoutes", allEntries = true)
    public void deleteHubRoute(UUID hubRouteId, Long userId) {
        HubRoute hubRoute = getHubRoute(hubRouteId);
        hubRoute.delete(userId);
    }

    private HubRoute getHubRoute(UUID hubRouteId) {
        return hubRouteRepository.findById(hubRouteId)
            .orElseThrow(
                () -> HubRouteBusinessException.from(HubRouteErrorCode.INVALID_HUB_ROUTE_REQUEST));
    }


    //각 허브의 위도와 경도를 통해 Haversine 공식을 사용하여 두 지점 간의 구면 거리를 구하는 방식
    private double calculateDistance(double fromLatitude, double fromLongitude, double toLatitude,
        double toLongitude) {

        double deltaLatitude = Math.toRadians(toLatitude - fromLatitude);
        double deltaLongitude = Math.toRadians(toLongitude - fromLongitude);

        double haversineFormula = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2)
            + Math.cos(Math.toRadians(fromLatitude)) * Math.cos(Math.toRadians(toLatitude))
            * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

        double centralAngle =
            2 * Math.atan2(Math.sqrt(haversineFormula), Math.sqrt(1 - haversineFormula));
        return EARTH_RADIUS_KM * centralAngle;
    }

}

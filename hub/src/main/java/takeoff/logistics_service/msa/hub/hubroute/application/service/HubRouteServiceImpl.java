package takeoff.logistics_service.msa.hub.hubroute.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.client.HubClient;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.client.NaverRequestClient;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.HubIdsDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PutHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteNaverResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PostHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PutHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteBusinessException;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteErrorCode;
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
public class HubRouteServiceImpl implements HubRouteService{

    private final HubRouteRepository hubRouteRepository;
    private final HubClient hubClient;
    private final NaverRequestClient naverRequestClient;


    @Override
    public PostHubRouteResponseDto createHubRoute(PostHubRouteRequestDto requestDto) {
        log.info("----------------------");
        List<GetRouteResponseDto> responseToHub = hubClient.findByToHubIdAndFromHubId(
            HubIdsDto.from(requestDto));

        GetHubRouteNaverResponseDto result = naverRequestClient.sendRequestToNaver(responseToHub)
            .onErrorMap(error -> {
                log.error("NaverAPI 응답을 받을 수 없습니다.", error);
                return HubRouteBusinessException.from(HubRouteErrorCode.NAVER_ERROR);
            })
            .block();


        HubRoute hubRoute = result.toEntity(requestDto);
        HubRoute savedHubRoute = hubRouteRepository.save(hubRoute);

        return PostHubRouteResponseDto.from(savedHubRoute);

    }

    @Override
    @Transactional(readOnly = true)
    public GetHubRouteResponseDto findByHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = getHubRoute(
            hubRouteId);
        return GetHubRouteResponseDto.from(hubRoute);
    }

    @Override
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
    public void deleteHubRoute(UUID hubRouteId, Long userId) {
        HubRoute hubRoute = getHubRoute(hubRouteId);
        hubRoute.delete(userId);
    }

    private HubRoute getHubRoute(UUID hubRouteId) {
        return hubRouteRepository.findById(hubRouteId)
            .orElseThrow(() -> HubRouteBusinessException.from(HubRouteErrorCode.INVALID_HUB_ROUTE_REQUEST));
    }


}

package takeoff.logistics_service.msa.hub.hubroute.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;
import takeoff.logistics_service.msa.hub.hubroute.domain.repository.HubRouteRepository;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PutHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.PutHubRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HubRouteServiceImpl implements HubRouteService{

    private final HubRouteRepository hubRouteRepository;

    @Override
    @Transactional(readOnly = true)
    public GetHubRouteResponseDto findByHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = hubRouteRepository.findById(hubRouteId)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 허브 이동경로 입니다."));
        return GetHubRouteResponseDto.from(hubRoute);
    }

    @Override
    public PutHubRouteResponseDto updateHubRoute(UUID hubRouteId,
        PutHubRouteRequestDto requestDto) {
        HubRoute hubRoute = hubRouteRepository.findById(hubRouteId)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 허브 이동경로 입니다."));
        hubRoute.modifyHubRoute(
            requestDto.fromHubId(),
            requestDto.toHubId(),
            requestDto.distance().getDistanceKm(),
            requestDto.duration().getDurationMinutes());
        return PutHubRouteResponseDto.from(hubRoute);
    }

    //baseEntity 생성후 수정
    @Override
    public void deleteHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = hubRouteRepository.findById(hubRouteId)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 허브 이동경로 입니다."));

    }

    //허브 이동경로 생성은 알고리즘 때문에 고민 좀 더 해보고 작성하겠습니다..
//    @Override
//    public List<PostHubRouteResponseDto> createHubRoute(PostHubRouteRequestDto requestDto) {
//
//    }




}

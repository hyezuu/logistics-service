package takeoff.logistics_service.msa.hub.hubroute.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PutHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.PutHubRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubRouteService {

    GetHubRouteResponseDto findByHubRoute(UUID hubRouteId);

    PutHubRouteResponseDto updateHubRoute(UUID hubRouteId, PutHubRouteRequestDto requestDto);

    void deleteHubRoute(UUID hubRouteId);

//    List<PostHubRouteResponseDto> createHubRoute(PostHubRouteRequestDto requestDto);
}

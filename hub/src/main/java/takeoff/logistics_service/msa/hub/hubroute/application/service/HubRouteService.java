package takeoff.logistics_service.msa.hub.hubroute.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubRoutesDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostDeliveryHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PutHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PostHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PutHubRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubRouteService {

    GetHubRouteResponseDto findByHubRoute(UUID hubRouteId);

    PutHubRouteResponseDto updateHubRoute(UUID hubRouteId, PutHubRouteRequestDto requestDto);

    void deleteHubRoute(UUID hubRouteId, Long userId);

    PostHubRouteResponseDto createHubRoute(PostHubRouteRequestDto requestDto);

    HubRoutesDto getDeliveryHubRouteList(PostDeliveryHubRouteRequestDto request);

}

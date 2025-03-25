package takeoff.logistics_service.msa.hub.hub.application.service;

import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.hub.hub.application.dto.HubIdsDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.feign.GetAllHubsDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.PatchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.SearchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PaginatedResultHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PatchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PostHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubService {

    PostHubResponseDto saveHub(PostHubRequestDto requestDto);

    PatchHubResponseDto updateHub(UUID hubId, PatchHubRequestDto requestDto);

    void deleteHub(UUID hubId, Long userId);

    GetHubResponseDto findByHubId(UUID hubId);

    PaginatedResultHubResponseDto searchHub(SearchHubRequestDto requestDto);

    List<GetRouteResponseDto> findByToHubIdAndFromHubId(HubIdsDto applicationDto);

    List<GetAllHubsDto> findAllHub();
}

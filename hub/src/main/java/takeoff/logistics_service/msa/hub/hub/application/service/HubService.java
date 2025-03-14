package takeoff.logistics_service.msa.hub.hub.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PatchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.GetHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PatchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubService {

    PostHubResponseDto saveHub(PostHubRequestDto requestDto);

    PatchHubResponseDto updateHub(UUID hubId, PatchHubRequestDto requestDto);

//    void deleteHub(UUID hubId);

    GetHubResponseDto findByHubId(UUID hubId);
}

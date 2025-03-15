package takeoff.logistics_service.msa.hub.hub.application.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PatchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.SearchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.GetHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PatchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.SearchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubService {

    PostHubResponseDto saveHub(PostHubRequestDto requestDto);

    PatchHubResponseDto updateHub(UUID hubId, PatchHubRequestDto requestDto);

//    void deleteHub(UUID hubId);

    GetHubResponseDto findByHubId(UUID hubId);

    Page<SearchHubResponseDto> searchHub(SearchHubRequestDto requestDto, Pageable pageable);
}

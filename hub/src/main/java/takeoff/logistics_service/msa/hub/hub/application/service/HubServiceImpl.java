package takeoff.logistics_service.msa.hub.hub.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.model.repository.HubRepository;
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
@Service
@RequiredArgsConstructor
@Transactional
public class HubServiceImpl implements HubService{

    private final HubRepository hubRepository;

    @Override
    public PostHubResponseDto saveHub(PostHubRequestDto requestDto) {
        return PostHubResponseDto.from(hubRepository.save(requestDto.toEntity()));
    }

    @Override
    public PatchHubResponseDto updateHub(UUID hubId, PatchHubRequestDto requestDto) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 허브입니다."));
        hub.modifyHubName(requestDto.hubName());
        return PatchHubResponseDto.from(hub);
    }

    @Override
    @Transactional(readOnly = true)
    public GetHubResponseDto findByHubId(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 허브입니다."));
        return GetHubResponseDto.from(hub);
    }

    @Override
    public Page<SearchHubResponseDto> searchHub(SearchHubRequestDto requestDto, Pageable pageable) {
        return hubRepository.searchHub(requestDto, pageable);
    }

    //      Auditing 설정시 추가 개발 예정
//    @Override
//    public void deleteHub(UUID hubId) {
//        Hub hub = hubRepository.findByHubId(hubId)
//            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 허브입니다."));v
//    }
}

package takeoff.logistics_service.msa.hub.hub.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hub.application.dto.HubIdsDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.feign.GetAllHubsDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.PatchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.SearchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PatchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PostHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.SearchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.exception.HubBusinessException;
import takeoff.logistics_service.msa.hub.hub.application.exception.HubErrorCode;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.domain.repository.HubRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;

    @Override
    public PostHubResponseDto saveHub(PostHubRequestDto requestDto) {
        return PostHubResponseDto.from(hubRepository.save(requestDto.toEntity()));
    }

    @Override
    public PatchHubResponseDto updateHub(UUID hubId, PatchHubRequestDto requestDto) {
        Hub hub = getHub(hubId);
        hub.modifyHubName(requestDto.hubName());
        return PatchHubResponseDto.from(hub);
    }

    @Override
    @Transactional(readOnly = true)
    public GetHubResponseDto findByHubId(UUID hubId) {
        Hub hub = getHub(hubId);
        return GetHubResponseDto.from(hub);
    }


    @Override
    public PaginatedResultDto<SearchHubResponseDto> searchHub(SearchHubRequestDto requestDto) {
        return PaginatedResultDto.from(hubRepository.searchHub(requestDto.toSearchCriteria()));
    }

    @Override
    public List<GetRouteResponseDto> findByToHubIdAndFromHubId(HubIdsDto hubIdsDto) {

        return hubRepository.findByIdIn(
                List.of(hubIdsDto.toHubId(), hubIdsDto.fromHubId()))
            .stream()
            .map(GetRouteResponseDto::from)
            .toList();
    }

    @Override
    public List<GetAllHubsDto> findByAllHub() {
        return hubRepository.findAll()
            .stream()
            .map(GetAllHubsDto::from)
            .toList();
    }

    @Override
    public void deleteHub(UUID hubId) {
        Hub hub = getHub(hubId);
        //Auth 개발시 수정 예정
        hub.delete(1L);
    }

    private Hub getHub(UUID hubId) {
        return hubRepository.findById(hubId)
            .orElseThrow(() -> HubBusinessException.from(HubErrorCode.HUB_NOT_FOUND));
    }
}

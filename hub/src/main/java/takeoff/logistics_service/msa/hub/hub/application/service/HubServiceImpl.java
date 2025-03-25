package takeoff.logistics_service.msa.hub.hub.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PaginatedResultHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PatchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PostHubResponseDto;
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
    @Caching(evict = {
        @CacheEvict(value = "hubs", key = "#hubId", cacheManager = "hubCacheManager")
    })
    public PatchHubResponseDto updateHub(UUID hubId, PatchHubRequestDto requestDto) {
        Hub hub = getHub(hubId);
        hub.modifyHubName(requestDto.hubName());
        return PatchHubResponseDto.from(hub);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "hubs", key = "#hubId", cacheManager = "hubCacheManager")
    public GetHubResponseDto findByHubId(UUID hubId) {
        Hub hub = getHub(hubId);
        return GetHubResponseDto.from(hub);
    }


    @Override
    @Cacheable(value = "hubSearch",
        key = "'search:' + #requestDto.hubName() + "
            + "'-adress:' + #requestDto.address() +"
            + "'-isAcs:' + #requestDto.isAsc() +"
            + "'-sortBy:' + #requestDto.sortBy() +"
            + "'-page:' + #requestDto.page() +"
            + "'-size:' + #requestDto.size() ",
        cacheManager = "hubListCacheManager"
    )
    public PaginatedResultHubResponseDto searchHub(SearchHubRequestDto requestDto) {
        return PaginatedResultHubResponseDto.from(PaginatedResultDto.from(hubRepository.searchHub(requestDto.toSearchCriteria())));
    }

    @Override
    public List<GetRouteResponseDto> findByToHubIdAndFromHubId(HubIdsDto hubIdsDto) {

        return (hubRepository.findByIdInAndDeletedAtIsNull(
                List.of(hubIdsDto.toHubId(), hubIdsDto.fromHubId()))
            .stream()
            .map(GetRouteResponseDto::from)
            .toList());
    }

    @Override
    public List<GetAllHubsDto> findAllHub() {
        return hubRepository.findByDeletedAtIsNull()
            .stream()
            .map(GetAllHubsDto::from)
            .toList();
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "hubs", key = "'allHubs'", cacheManager = "hubCacheManager"),
        @CacheEvict(value = "hubs", key = "#hubId", cacheManager = "hubCacheManager"),
        @CacheEvict(value = "hubsRoute", allEntries = true, cacheManager = "hubListCacheManager"),
        @CacheEvict(value = "hubSearch", allEntries = true, cacheManager = "hubListCacheManager")
    })
    public void deleteHub(UUID hubId, Long userId) {
        Hub hub = getHub(hubId);
        hub.delete(userId);
    }

    private Hub getHub(UUID hubId) {
        return hubRepository.findByIdAndDeletedAtIsNull(hubId)
            .orElseThrow(() -> HubBusinessException.from(HubErrorCode.HUB_NOT_FOUND));
    }
}

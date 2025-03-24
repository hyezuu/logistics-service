package takeoff.logistics_service.msa.hub.hub.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteria;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteriaResponse;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.PaginatedResult;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubRepository {

    Hub save(Hub hub);

    Optional<Hub> findByIdAndDeletedAtIsNull(UUID hubId);

    PaginatedResult<HubSearchCriteriaResponse> searchHub(HubSearchCriteria hubSearchCriteria);

    List<Hub> findByIdInAndDeletedAtIsNull(List<UUID> ids);

    List<Hub> findByDeletedAtIsNull();
}

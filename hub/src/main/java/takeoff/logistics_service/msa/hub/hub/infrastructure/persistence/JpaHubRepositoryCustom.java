package takeoff.logistics_service.msa.hub.hub.infrastructure.persistence;

import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteria;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteriaResponse;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.PaginatedResult;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface JpaHubRepositoryCustom {
    PaginatedResult<HubSearchCriteriaResponse> searchHub(HubSearchCriteria hubSearchCriteria);

}

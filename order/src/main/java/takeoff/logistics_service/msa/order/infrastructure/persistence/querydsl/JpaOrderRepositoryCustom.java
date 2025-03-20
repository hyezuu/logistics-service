package takeoff.logistics_service.msa.order.infrastructure.persistence.querydsl;

import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteria;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteriaResponse;
import takeoff.logistics_service.msa.order.domain.repository.search.PaginatedResult;

public interface JpaOrderRepositoryCustom {

  public PaginatedResult<OrderSearchCriteriaResponse> findAllBySearchParams(
      OrderSearchCriteria criteria);
}

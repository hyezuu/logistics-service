package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.persistence.querydsl;

import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteria;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteriaResponse;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.PaginatedResult;

public interface JpaDeliveryRepositoryCustom {

  PaginatedResult<DeliverySearchCriteriaResponse> findAllBySearchParams(
      DeliverySearchCriteria criteria
  );
}

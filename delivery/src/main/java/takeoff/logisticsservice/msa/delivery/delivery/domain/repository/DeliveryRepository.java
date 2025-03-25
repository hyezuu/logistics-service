package takeoff.logisticsservice.msa.delivery.delivery.domain.repository;

import java.util.List;
import java.util.Optional;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryId;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteria;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteriaResponse;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.PaginatedResult;

public interface DeliveryRepository {

  Delivery save(Delivery delivery);

  Optional<Delivery> findById(DeliveryId deliveryId);

  List<Delivery> findAllByDeliveryManagerId(Long deliveryManagerId);

  PaginatedResult<DeliverySearchCriteriaResponse> findAllBySearchParams(
      DeliverySearchCriteria criteria
  );
}

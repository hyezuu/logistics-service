package takeoff.logisticsservice.msa.delivery.delivery.application.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteria;

public record SearchDeliveryRequestDto(
    UUID orderId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean isAsc,
    String sortBy,
    Integer page,
    Integer size
) {

  public DeliverySearchCriteria toSearchCriteria() {
    return new DeliverySearchCriteria(
        orderId,
        null,
        null,
        null,
        startDate,
        endDate,
        isAsc,
        sortBy,
        page,
        size);
  }

  public DeliverySearchCriteria toSearchCriteria(UUID hubId, Long customerId, Long deliveryManagerId) {
    return new DeliverySearchCriteria(
        orderId,
        hubId,
        customerId,
        deliveryManagerId,
        startDate,
        endDate,
        isAsc,
        sortBy,
        page,
        size);
  }
}

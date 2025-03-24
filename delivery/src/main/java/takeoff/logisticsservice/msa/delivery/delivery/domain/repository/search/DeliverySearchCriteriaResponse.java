package takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryStatus;

public record DeliverySearchCriteriaResponse(
    UUID deliveryId,
    UUID orderId,
    DeliveryStatus status,
    Long deliveryManagerId,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {

  public static DeliverySearchCriteriaResponse from(Delivery delivery) {
    return new DeliverySearchCriteriaResponse(
        delivery.getIdLiteral(),
        delivery.getOrderId(),
        delivery.getStatus(),
        delivery.getDeliveryManagerId(),
        delivery.getCreatedAt(),
        delivery.getCreatedBy(),
        delivery.getUpdatedAt(),
        delivery.getUpdatedBy()
    );
  }
}

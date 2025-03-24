package takeoff.logisticsservice.msa.delivery.delivery.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteriaResponse;

public record SearchDeliveryResponseDto(
    UUID deliveryId,
    UUID orderId,
    String deliveryStatus,
    Long deliveryManagerId,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {

  public static SearchDeliveryResponseDto from(DeliverySearchCriteriaResponse response) {
    return new SearchDeliveryResponseDto(
        response.deliveryId(),
        response.orderId(),
        response.status().getStatus(),
        response.deliveryManagerId(),
        response.createdAt(),
        response.createdBy(),
        response.updatedAt(),
        response.updatedBy()
    );
  }
}

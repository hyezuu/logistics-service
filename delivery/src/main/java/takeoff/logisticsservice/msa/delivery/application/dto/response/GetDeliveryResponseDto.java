package takeoff.logisticsservice.msa.delivery.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.domain.entity.Delivery;

public record GetDeliveryResponseDto(
    UUID deliveryId,
    UUID orderId,
    String deliveryStatus,
    UUID fromHubId,
    UUID toHubId,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {

  public static GetDeliveryResponseDto from(Delivery delivery) {
    return new GetDeliveryResponseDto(
        delivery.getId().getId(),
        delivery.getOrderId(),
        delivery.getStatus().getStatus(),
        delivery.getFromHubId(),
        delivery.getToHubId(),
        delivery.getCreatedAt(),
        delivery.getCreatedBy(),
        delivery.getUpdatedAt(),
        delivery.getUpdatedBy()
    );
  }
}

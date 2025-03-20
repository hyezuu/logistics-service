package takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.response.GetDeliveryResponseDto;

public record GetDeliveryResponse(
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

  public static GetDeliveryResponse from(GetDeliveryResponseDto dto) {
    return new GetDeliveryResponse(
        dto.deliveryId(),
        dto.orderId(),
        dto.deliveryStatus(),
        dto.fromHubId(),
        dto.toHubId(),
        dto.createdAt(),
        dto.createdBy(),
        dto.updatedAt(),
        dto.updatedBy()
    );
  }
}

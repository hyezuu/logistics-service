package takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.response.SearchDeliveryResponseDto;

public record SearchDeliveryResponse(
    UUID deliveryId,
    UUID orderId,
    String status,
    Long deliveryManagerId,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {

  public static SearchDeliveryResponse from(SearchDeliveryResponseDto dto) {
    return new SearchDeliveryResponse(
        dto.deliveryId(),
        dto.orderId(),
        dto.deliveryStatus(),
        dto.deliveryManagerId(),
        dto.createdAt(),
        dto.createdBy(),
        dto.updatedAt(),
        dto.updatedBy()
    );
  }
}

package takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.response.DeliveryRouteDto;

public record DeliveryRouteApi(
    UUID deliveryRouteId,
    UUID deliveryId,
    Long deliveryManagerId,
    Integer sequenceNumber,
    UUID fromHubId,
    UUID toHubId,
    Integer estimatedDistance,
    Integer estimatedDuration,
    Integer actualDistance,
    Integer actualDuration,
    String status,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {

  public static DeliveryRouteApi from(DeliveryRouteDto dto) {
    return new DeliveryRouteApi(
        dto.deliveryRouteId(),
        dto.deliveryId(),
        dto.deliveryManagerId(),
        dto.sequenceNumber(),
        dto.fromHubId(),
        dto.toHubId(),
        dto.estimatedDistance(),
        dto.estimatedDuration(),
        dto.actualDistance(),
        dto.actualDuration(),
        dto.status(),
        dto.createdAt(),
        dto.createdBy(),
        dto.updatedAt(),
        dto.updatedBy()
    );
  }
}

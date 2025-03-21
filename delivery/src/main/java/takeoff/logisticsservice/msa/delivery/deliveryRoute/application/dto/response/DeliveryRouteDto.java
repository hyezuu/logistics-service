package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;

public record DeliveryRouteDto(
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

  public static DeliveryRouteDto from(DeliveryRoute deliveryRoute) {
    return new DeliveryRouteDto(
        deliveryRoute.getId(),
        deliveryRoute.getDeliveryId(),
        deliveryRoute.getDeliveryManagerId(),
        deliveryRoute.getSequenceNumber(),
        deliveryRoute.getFromHubId(),
        deliveryRoute.getToHubId(),
        deliveryRoute.getEstimatedDistance(),
        deliveryRoute.getEstimatedDuration(),
        deliveryRoute.getActualDistance(),
        deliveryRoute.getActualDuration(),
        deliveryRoute.getStatusLiteral(),
        deliveryRoute.getCreatedAt(),
        deliveryRoute.getCreatedBy(),
        deliveryRoute.getUpdatedAt(),
        deliveryRoute.getUpdatedBy()
    );
  }
}

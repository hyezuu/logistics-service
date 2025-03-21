package takeoff.logistics_service.msa.order.application.client.dto.request;

import java.util.UUID;

public record PostDeliveryRoutesRequestDto(
    UUID deliveryId,
    UUID departureHubId,
    UUID destinationHubId
) {
}

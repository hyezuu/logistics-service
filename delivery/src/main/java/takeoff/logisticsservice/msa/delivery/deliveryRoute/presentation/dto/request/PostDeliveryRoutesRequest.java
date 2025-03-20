package takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.request;

import java.util.UUID;

public record PostDeliveryRouteRequest(
    UUID deliveryId,
    UUID departureHubId,
    UUID destinationHubId
) {

}

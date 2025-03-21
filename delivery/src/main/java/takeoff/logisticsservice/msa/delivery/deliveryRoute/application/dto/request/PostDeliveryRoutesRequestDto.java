package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.request;

import java.util.UUID;

public record PostDeliveryRoutesRequestDto(UUID deliveryId,
                                           UUID departureHubId,
                                           UUID destinationHubId) {

}

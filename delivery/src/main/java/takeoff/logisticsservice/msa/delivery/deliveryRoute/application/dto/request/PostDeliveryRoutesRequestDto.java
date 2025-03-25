package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record PostDeliveryRoutesRequestDto(UUID deliveryId,
                                           UUID departureHubId,
                                           UUID destinationHubId) {

}

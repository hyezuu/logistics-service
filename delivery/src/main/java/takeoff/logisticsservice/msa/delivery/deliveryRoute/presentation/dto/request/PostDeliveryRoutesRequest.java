package takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.request;

import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.request.PostDeliveryRoutesRequestDto;

public record PostDeliveryRoutesRequest(
    UUID deliveryId,
    UUID departureHubId,
    UUID destinationHubId
) {

  public PostDeliveryRoutesRequestDto toApplicationDto() {
    return new PostDeliveryRoutesRequestDto(
        deliveryId,
        departureHubId,
        destinationHubId
    );
  }
}

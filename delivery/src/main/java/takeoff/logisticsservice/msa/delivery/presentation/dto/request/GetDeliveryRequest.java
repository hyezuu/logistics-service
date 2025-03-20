package takeoff.logisticsservice.msa.delivery.presentation.dto.request;

import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.application.dto.request.GetDeliveryRequestDto;

public record GetDeliveryRequest(
    UUID deliveryId,
    UUID orderId
) {

  public GetDeliveryRequestDto toApplicationDto() {
    return new GetDeliveryRequestDto(
        deliveryId,
        orderId
    );
  }
}

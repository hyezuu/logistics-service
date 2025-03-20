package takeoff.logisticsservice.msa.delivery.application.dto.request;

import java.util.UUID;

public record GetDeliveryRequestDto(
    UUID deliveryId,
    UUID orderId
) {

}

package takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request;

import java.util.UUID;

public record PatchDeliveryRequestDto(
    UUID deliveryId,
    String status) {

}

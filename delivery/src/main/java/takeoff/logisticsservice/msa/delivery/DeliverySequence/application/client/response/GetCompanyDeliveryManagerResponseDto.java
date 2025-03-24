package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response;

import java.util.UUID;

public record GetCompanyDeliveryManagerResponseDto(
    Long userId,
    UUID hubId,
    Integer deliverySequence
) {
}

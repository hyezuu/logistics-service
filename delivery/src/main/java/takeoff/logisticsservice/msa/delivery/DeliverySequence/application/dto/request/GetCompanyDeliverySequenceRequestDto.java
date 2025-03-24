package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.request;

import java.util.UUID;

public record GetCompanyDeliverySequenceRequestDto(
    UUID hubId
) {
}

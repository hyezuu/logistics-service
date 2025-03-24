package takeoff.logisticsservice.msa.delivery.delivery.application.client.dto.request;

import java.util.UUID;

public record GetCompanyDeliverySequenceRequestDto(
    UUID hubId
) {
}

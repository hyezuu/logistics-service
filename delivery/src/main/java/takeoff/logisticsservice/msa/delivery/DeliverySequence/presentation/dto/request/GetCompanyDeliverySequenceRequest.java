package takeoff.logisticsservice.msa.delivery.DeliverySequence.presentation.dto.request;

import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.request.GetCompanyDeliverySequenceRequestDto;

public record GetCompanyDeliverySequenceRequest(
    UUID hubId
) {
  public GetCompanyDeliverySequenceRequestDto toApplicationDto() {
    return new GetCompanyDeliverySequenceRequestDto(
        hubId
    );
  }
}

package takeoff.logisticsservice.msa.delivery.DeliverySequence.presentation.dto.response;

import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.response.GetHubDeliverySequenceResponseDto;

public record GetHubDeliverySequenceResponse(
    Long nextHubDeliveryManagerId
) {

  public static GetHubDeliverySequenceResponse from(GetHubDeliverySequenceResponseDto dto) {
    return new GetHubDeliverySequenceResponse(
        dto.nextHubDeliveryManagerId()
    );
  }
}

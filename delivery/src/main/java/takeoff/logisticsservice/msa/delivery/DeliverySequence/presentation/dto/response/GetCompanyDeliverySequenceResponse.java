package takeoff.logisticsservice.msa.delivery.DeliverySequence.presentation.dto.response;

import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.response.GetCompanyDeliverySequenceResponseDto;

public record GetCompanyDeliverySequenceResponse(
    Long companyDeliveryManagerId
) {

  public static GetCompanyDeliverySequenceResponse from(GetCompanyDeliverySequenceResponseDto dto) {
    return new GetCompanyDeliverySequenceResponse(
        dto.companyDeliveryManagerId()
    );
  }
}

package takeoff.logisticsservice.msa.delivery.DeliverySequence.presentation.internal;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.DeliverySequenceService;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.presentation.dto.request.GetCompanyDeliverySequenceRequest;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.presentation.dto.response.GetCompanyDeliverySequenceResponse;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.presentation.dto.response.GetHubDeliverySequenceResponse;

@RestController
@RequestMapping("/api/v1/app/deliverySequences")
@RequiredArgsConstructor
public class DeliverySequenceInternalController {

  private final DeliverySequenceService deliverySequenceService;

  @GetMapping("/nextCompanyDeliverySequence")
  public GetCompanyDeliverySequenceResponse findNextCompanyDeliverySequence(
      GetCompanyDeliverySequenceRequest request
  ) {
    return GetCompanyDeliverySequenceResponse.from(
        deliverySequenceService.findNextCompanyDeliverySequence(request.toApplicationDto())
    );
  }

  @GetMapping("/nextHubDeliverySequence")
  public GetHubDeliverySequenceResponse findNextHubDeliverySequence() {
    return GetHubDeliverySequenceResponse.from(
        deliverySequenceService.findNextHubDeliverySequence()
    );
  }
}

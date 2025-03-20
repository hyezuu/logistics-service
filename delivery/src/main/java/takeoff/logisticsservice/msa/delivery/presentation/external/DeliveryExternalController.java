package takeoff.logisticsservice.msa.delivery.presentation.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logisticsservice.msa.delivery.application.DeliveryService;
import takeoff.logisticsservice.msa.delivery.presentation.dto.request.GetDeliveryRequest;
import takeoff.logisticsservice.msa.delivery.presentation.dto.response.GetDeliveryResponse;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryExternalController {

  private final DeliveryService deliveryService;

  @GetMapping
  public ResponseEntity<GetDeliveryResponse> searchDelivery(
      @ModelAttribute GetDeliveryRequest request
  ) {
    return ResponseEntity.ok()
        .body(GetDeliveryResponse.from(deliveryService.searchDelivery(
            request.toApplicationDto()
        )));
  }
}

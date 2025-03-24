package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.GetHubDeliverySequenceResponseDto;

@Component
@FeignClient(name = "deliverySequenceInternalDeliveryRoute")
public interface DeliverySequenceClientInternalDeliveryRoute {

  @GetMapping("/api/v1/app/deliveryRoutes/nextHubDeliverySequence")
  GetHubDeliverySequenceResponseDto findNextHubDeliverySequence();

}

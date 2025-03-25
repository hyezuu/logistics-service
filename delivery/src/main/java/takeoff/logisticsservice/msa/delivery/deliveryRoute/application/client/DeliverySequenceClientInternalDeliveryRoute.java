package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.GetHubDeliverySequenceResponseDto;


@FeignClient(name = "delivery", contextId = "deliveryRoute-deliverySequence-context"
,configuration = FeignClientConfig.class)
public interface DeliverySequenceClientInternalDeliveryRoute {

  @GetMapping("/api/v1/app/deliverySequences/nextHubDeliverySequence")
  GetHubDeliverySequenceResponseDto findNextHubDeliverySequence();

}

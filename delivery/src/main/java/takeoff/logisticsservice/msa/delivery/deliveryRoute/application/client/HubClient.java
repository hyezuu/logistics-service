package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.request.PostHubRouteRequestDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.PostHubRouteResponseDto;

@Component
@FeignClient(name = "hub")
public interface HubClient {

  @PostMapping("/api/v1/app/hubRoutes/delivery")
  PostHubRouteResponseDto postHubRoute(@RequestBody PostHubRouteRequestDto dto);

}

package takeoff.logistics_service.msa.order.application.client;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRoutesRequestDto;

@Component
@FeignClient(name = "delivery")
public interface DeliveryClient {

  @PostMapping("/api/v1/app/deliveries")
  public UUID saveDelivery(UUID orderId);

  @PostMapping("api/v1/app/deliveryRoutes")
  public List<UUID> saveDeliveryRoute(PostDeliveryRoutesRequestDto dto);
}

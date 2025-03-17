package takeoff.logistics_service.msa.order.application.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "delivery")
public interface DeliveryClient {

  @PostMapping("/api/v1/app/delivery")
  public UUID saveDelivery();
}

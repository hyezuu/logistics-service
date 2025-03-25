package takeoff.logistics_service.msa.order.application.client;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRoutesRequestDto;

@Component
@FeignClient(name = "delivery", configuration = FeignClientConfig.class)
public interface DeliveryClient {

  @GetMapping("/api/v1/app/deliveries")
  List<UUID> findAllDeliveryIdByUser(@RequestParam(name = "userId") Long userId);

  @PostMapping("/api/v1/app/deliveries")
  UUID saveDelivery(PostDeliveryRequestDto dto);

  @DeleteMapping("/api/v1/app/deliveries/{deliveryId}")
  void deleteDelivery(@PathVariable("deliveryId") UUID deliveryId);

  @PostMapping("/api/v1/app/deliveryRoutes")
  List<UUID> saveDeliveryRoute(@RequestBody PostDeliveryRoutesRequestDto dto);

  @GetMapping("api/v1/app/deliveryRoutes/{deliveryManagerId}")
  List<UUID> findAllDeliveryRoutes_DeliveryIdByDeliveryManagerId(
      @PathVariable(name = "deliveryManagerId") Long deliveryManagerId);

  @DeleteMapping("api/v1/app/deliveryRoutes/{deliveryId}")
  void deleteDeliveryRoutes(@PathVariable("deliveryId") UUID deliveryId);
}

package takeoff.logistics_service.msa.order.application.client;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRoutesRequestDto;

@Component
@FeignClient(name = "delivery")
public interface DeliveryClient {

  @GetMapping("/api/v1/app/deliveries")
  List<UUID> findAllDeliveryIdByUser(Long userId);

  @PostMapping("/api/v1/app/deliveries")
  UUID saveDelivery(PostDeliveryRequestDto dto);

  @PostMapping("api/v1/app/deliveryRoutes")
  List<UUID> saveDeliveryRoute(PostDeliveryRoutesRequestDto dto);

  @GetMapping("api/v1/app/deliveryRoutes")
  List<UUID> findAllDeliveryRoutes_DeliveryIdByDeliveryManagerId(
      @RequestParam(name = "deliveryManagerId") Long deliveryManagerId);

  @DeleteMapping("/api/v1/app/deliveries/{deliveryId}")
  void deleteDelivery(@PathVariable("deliveryId") UUID deliveryId);

  @DeleteMapping("api/v1/app/deliveryRoutes")
  public void deleteDeliveryRoutes(UUID deliveryId);
}

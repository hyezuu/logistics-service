package takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.internal;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.service.DeliveryRouteService;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.request.PostDeliveryRoutesRequest;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.response.GetDeliveryRoutesResponse;

@RestController
@RequestMapping("api/v1/app/deliveryRoutes")
@RequiredArgsConstructor
public class DeliveryRouteInternalController {

  private final DeliveryRouteService deliveryRouteService;

  @PostMapping
  public List<UUID> saveDeliveryRoutes(PostDeliveryRoutesRequest request) {
    return deliveryRouteService.saveDeliveryRoutes(request.toApplicationDto());
  }

  @GetMapping
  public GetDeliveryRoutesResponse findAllDeliveryRoutesByDeliveryId(UUID deliveryId) {
    return GetDeliveryRoutesResponse.from(
        deliveryRouteService.findAllDeliveryRoutesByDeliveryId(deliveryId));
  }

  @DeleteMapping
  public void deleteDeliveryRoutes(UUID deliveryId) {
    deliveryRouteService.DeleteDeliveryRoutes(deliveryId);
  }

}

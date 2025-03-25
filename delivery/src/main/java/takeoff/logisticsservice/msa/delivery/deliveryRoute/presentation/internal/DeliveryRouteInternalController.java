package takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.internal;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.service.DeliveryRouteService;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.request.PostDeliveryRoutesRequest;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.response.GetDeliveryRoutesResponse;

@RestController
@RequestMapping("api/v1/app/deliveryRoutes")
@RequiredArgsConstructor
public class DeliveryRouteInternalController {

  private final DeliveryRouteService deliveryRouteService;

  @PostMapping
  public List<UUID> saveDeliveryRoutes(@RequestBody PostDeliveryRoutesRequest request) {
    return deliveryRouteService.saveDeliveryRoutes(request.toApplicationDto());
  }

  @GetMapping
  public GetDeliveryRoutesResponse findAllDeliveryRoutesByDeliveryId(
      @RequestParam(name = "deliveryId") UUID deliveryId) {
    return GetDeliveryRoutesResponse.from(
        deliveryRouteService.findAllDeliveryRoutesByDeliveryId(deliveryId));
  }

  @GetMapping("/{deliveryManagerId}")
  public List<UUID> findAllDeliveryRoutes_DeliveryIdByDeliveryManagerId(
      @PathVariable(name = "deliveryManagerId") Long deliveryManagerId) {
    return deliveryRouteService.findAllDeliveryRoutes_DeliveryIdByDeliveryManagerId(
        deliveryManagerId);
  }

  @DeleteMapping("/{deliveryId}")
  public void deleteDeliveryRoutes(@PathVariable("deliveryId") UUID deliveryId,
      @UserInfo UserInfoDto user) {
    deliveryRouteService.DeleteDeliveryRoutes(deliveryId, user.userId());
  }

}

package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.response;

import java.util.List;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;

public record GetDeliveryRouteResponseDto(List<DeliveryRouteDto> deliveryRouteDtos) {

  public static GetDeliveryRouteResponseDto of(List<DeliveryRoute> deliveryRoutes) {
    return new GetDeliveryRouteResponseDto(
        deliveryRoutes.stream()
            .map(DeliveryRouteDto::from)
            .toList()
    );
  }
}

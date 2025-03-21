package takeoff.logisticsservice.msa.delivery.deliveryRoute.presentation.dto.response;

import java.util.List;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.dto.response.GetDeliveryRouteResponseDto;

public record GetDeliveryRoutesResponse(List<DeliveryRouteApi> deliveryRouteApis) {

  public static GetDeliveryRoutesResponse from(GetDeliveryRouteResponseDto dto) {
    return new GetDeliveryRoutesResponse(
        dto.deliveryRouteDtos()
            .stream()
            .map(DeliveryRouteApi::from)
            .toList()
    );
  }
}

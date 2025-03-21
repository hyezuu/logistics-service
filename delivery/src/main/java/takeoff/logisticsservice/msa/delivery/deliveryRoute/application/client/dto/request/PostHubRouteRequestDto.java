package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.request;

import java.util.UUID;

public record PostHubRouteRequestDto(
    UUID fromHubId,
    UUID toHubId
) {

}

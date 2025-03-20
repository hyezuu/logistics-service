package takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostDeliveryHubRouteRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public record PostDeliveryHubRouteRequest(UUID fromHubId,
                                          UUID toHubId) {

    public PostDeliveryHubRouteRequestDto toApplication() {
        return PostDeliveryHubRouteRequestDto.builder()
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }

}

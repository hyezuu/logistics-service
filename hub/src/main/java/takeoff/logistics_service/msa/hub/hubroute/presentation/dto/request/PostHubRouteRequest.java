package takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostHubRouteRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public record PostHubRouteRequest(UUID fromHubId,
                                  UUID toHubId) {

    public PostHubRouteRequestDto toApplication() {
        return PostHubRouteRequestDto.builder()
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }

}

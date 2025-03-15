package takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request;

import java.util.UUID;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public record PostHubRouteRequestDto(UUID fromHubId,
                                     UUID toHubId) {

}

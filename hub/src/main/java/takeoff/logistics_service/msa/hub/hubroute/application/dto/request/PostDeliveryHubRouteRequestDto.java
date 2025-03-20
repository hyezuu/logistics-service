package takeoff.logistics_service.msa.hub.hubroute.application.dto.request;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PostDeliveryHubRouteRequestDto(UUID fromHubId,
                                             UUID toHubId) {


}

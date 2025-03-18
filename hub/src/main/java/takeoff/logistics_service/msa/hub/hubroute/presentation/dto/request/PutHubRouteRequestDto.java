package takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public record PutHubRouteRequestDto(UUID fromHubId,
                                    UUID toHubId,
                                    Distance distance,
                                    Duration duration) {

}

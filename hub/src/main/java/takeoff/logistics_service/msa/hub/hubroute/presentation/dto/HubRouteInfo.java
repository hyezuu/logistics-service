package takeoff.logistics_service.msa.hub.hubroute.presentation.dto;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record HubRouteInfo(Distance distance,
                           Duration duration) {
}

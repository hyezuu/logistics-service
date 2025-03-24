package takeoff.logistics_service.msa.hub.hub.application.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */

@Builder
public record HubIdsDto(UUID fromHubId,
                        UUID toHubId) {

}

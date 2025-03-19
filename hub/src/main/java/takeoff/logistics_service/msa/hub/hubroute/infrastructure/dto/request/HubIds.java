package takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.request;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.HubIdsDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@Builder
public record HubIds(UUID toHubId,
                     UUID fromHubId) {
    public static HubIds from(HubIdsDto hubIdsDto) {
        return HubIds.builder()
            .toHubId(hubIdsDto.toHubId())
            .fromHubId(hubIdsDto.fromHubId())
            .build();
    }

}

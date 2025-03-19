package takeoff.logistics_service.msa.hub.hub.presentation.dto;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hub.application.dto.HubIdsDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
public record HubIds(UUID fromHubId,
                     UUID toHubId) {

    public HubIdsDto toApplicationDto() {
        return HubIdsDto.builder()
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }
}

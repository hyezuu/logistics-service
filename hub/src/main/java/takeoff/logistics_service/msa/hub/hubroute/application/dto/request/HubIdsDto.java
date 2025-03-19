package takeoff.logistics_service.msa.hub.hubroute.application.dto.request;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@Builder
public record HubIdsDto(UUID toHubId,
                        UUID fromHubId) {
    public static HubIdsDto from(PostHubRouteRequestDto requestDto) {
        return HubIdsDto.builder()
            .toHubId(requestDto.toHubId())
            .fromHubId(requestDto.fromHubId())
            .build();
    }
}

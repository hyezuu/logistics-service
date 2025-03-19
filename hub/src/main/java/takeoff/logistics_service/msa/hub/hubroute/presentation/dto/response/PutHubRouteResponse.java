package takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PutHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PutHubRouteResponse(UUID hubRouteId,
                                  UUID fromHubId,
                                  UUID toHubId,
                                  Distance distance,
                                  Duration duration) {

    public static PutHubRouteResponse from(PutHubRouteResponseDto putHubRouteResponseDto) {
        return PutHubRouteResponse.builder()
            .hubRouteId(putHubRouteResponseDto.hubRouteId())
            .fromHubId(putHubRouteResponseDto.fromHubId())
            .toHubId(putHubRouteResponseDto.toHubId())
            .distance(putHubRouteResponseDto.distance())
            .duration(putHubRouteResponseDto.duration())
            .build();
    }
}

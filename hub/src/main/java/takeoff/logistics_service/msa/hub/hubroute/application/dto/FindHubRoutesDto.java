package takeoff.logistics_service.msa.hub.hubroute.application.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record FindHubRoutesDto(UUID hubRouteId,
                               UUID fromHubId,
                               UUID toHubId,
                               DistanceDto distanceDto,
                               DurationDto durationDto) {

    public static FindHubRoutesDto from(HubRoute hubRoute) {
        return FindHubRoutesDto.builder()
            .hubRouteId(hubRoute.getId())
            .fromHubId(hubRoute.getFromHubId())
            .toHubId(hubRoute.getToHubId())
            .distanceDto(DistanceDto.from(hubRoute.getDistance()))
            .durationDto(DurationDto.from(hubRoute.getDuration()))
            .build();
    }

}

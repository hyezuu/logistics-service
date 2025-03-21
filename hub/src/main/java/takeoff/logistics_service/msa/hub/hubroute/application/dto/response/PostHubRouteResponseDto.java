package takeoff.logistics_service.msa.hub.hubroute.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DistanceDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DurationDto;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PostHubRouteResponseDto(UUID hubRouteId,
                                      DistanceDto distanceDto,
                                      DurationDto durationDto) {

    public static PostHubRouteResponseDto from(HubRoute hubRoute) {
        return PostHubRouteResponseDto.builder()
            .hubRouteId(hubRoute.getId())
            .distanceDto(DistanceDto.from(hubRoute.getDistance()))
            .durationDto(DurationDto.from(hubRoute.getDuration()))
            .build();
    }

    //테스트용
    public HubRoute toEntity(UUID fromHubId, UUID toHubId) {
        return HubRoute.builder()
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .distance(Distance.create(distanceDto.distance()))
            .duration(Duration.create(durationDto.duration()))
            .build();
    }


}

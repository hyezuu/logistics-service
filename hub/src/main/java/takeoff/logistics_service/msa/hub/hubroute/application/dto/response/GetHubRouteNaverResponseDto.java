package takeoff.logistics_service.msa.hub.hubroute.application.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record GetHubRouteNaverResponseDto(Integer distance,
                                          Integer duration) {

    public static HubRoute toEntity(GetHubRouteNaverResponseDto getHubRouteNaverResponseDto,
        PostHubRouteRequestDto postHubRouteRequestDto) {
        return HubRoute.builder()
            .toHubId(postHubRouteRequestDto.toHubId())
            .fromHubId(postHubRouteRequestDto.fromHubId())
            .distance(Distance.create(getHubRouteNaverResponseDto.distance()))
            .duration(Duration.create(getHubRouteNaverResponseDto.duration()))
            .build();
    }
}

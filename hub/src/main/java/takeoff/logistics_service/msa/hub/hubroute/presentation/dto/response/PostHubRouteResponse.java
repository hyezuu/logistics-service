package takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PostHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.DistanceApi;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.DurationApi;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PostHubRouteResponse(UUID hubRouteId,
                                   DistanceApi distanceApi,
                                   DurationApi durationApi) {

    public static PostHubRouteResponse from(PostHubRouteResponseDto responseDto) {
        return PostHubRouteResponse.builder()
            .hubRouteId(responseDto.hubRouteId())
            .distanceApi(DistanceApi.from(responseDto.distanceDto()))
            .durationApi(DurationApi.from(responseDto.durationDto()))
            .build();
    }

}

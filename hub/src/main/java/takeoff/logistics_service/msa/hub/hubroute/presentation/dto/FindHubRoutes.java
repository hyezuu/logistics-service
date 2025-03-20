package takeoff.logistics_service.msa.hub.hubroute.presentation.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.FindHubRoutesDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record FindHubRoutes(UUID hubRouteId,
                            UUID fromHubId,
                            UUID toHubId,
                            DistanceApi distanceApi,
                            DurationApi durationApi) {

    public static FindHubRoutes from(FindHubRoutesDto findHubRoutesDto) {
        return FindHubRoutes.builder()
            .hubRouteId(findHubRoutesDto.hubRouteId())
            .fromHubId(findHubRoutesDto.fromHubId())
            .toHubId(findHubRoutesDto.toHubId())
            .distanceApi(DistanceApi.from(findHubRoutesDto.distanceDto()))
            .durationApi(DurationApi.from(findHubRoutesDto.durationDto()))
            .build();
    }

}

package takeoff.logistics_service.msa.hub.hubroute.presentation.dto;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DistanceDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record DistanceApi(int distance) {

    public static DistanceApi from(
        DistanceDto distanceDto) {
        return takeoff.logistics_service.msa.hub.hubroute.presentation.dto.DistanceApi.builder()
            .distance(distanceDto.distance())
            .build();
    }
}

package takeoff.logistics_service.msa.hub.hubroute.application.dto;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record DistanceDto(int distance) {

    public static DistanceDto from(Distance distance) {
        return DistanceDto.builder()
            .distance(distance.getDistanceKm())
            .build();
    }


}

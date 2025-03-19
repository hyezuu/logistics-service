package takeoff.logistics_service.msa.hub.hubroute.application.dto;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record DurationDto(int duration) {

    public static DurationDto from(Duration duration) {
        return DurationDto.builder()
            .duration(duration.getDurationMinutes())
            .build();
    }
}

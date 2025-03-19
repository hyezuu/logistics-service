package takeoff.logistics_service.msa.hub.hubroute.presentation.dto;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DurationDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record DurationApi(int duration) {

    public static DurationApi from(DurationDto durationDto) {
        return DurationApi.builder()
            .duration(durationDto.duration())
            .build();
    }
}

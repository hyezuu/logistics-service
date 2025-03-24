package takeoff.logistics_service.msa.hub.hub.application.dto.feign;

import java.util.List;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 25.
 */
@Builder
public record AllHubListResponseDto(List<GetAllHubsDto> hubsDtoList) {

    public static AllHubListResponseDto from(List<GetAllHubsDto> list) {
        return AllHubListResponseDto.builder()
            .hubsDtoList(list)
            .build();
    }
}

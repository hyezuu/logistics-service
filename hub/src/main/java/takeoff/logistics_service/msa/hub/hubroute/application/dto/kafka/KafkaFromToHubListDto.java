package takeoff.logistics_service.msa.hub.hubroute.application.dto.kafka;

import java.util.List;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Builder
public record KafkaFromToHubListDto(List<GetRouteResponseDto> fromToHubList) {

    public static KafkaFromToHubListDto toKafka(List<GetRouteResponseDto> fromToHubList) {
        return KafkaFromToHubListDto.builder()
            .fromToHubList(fromToHubList)
            .build();
    }
}

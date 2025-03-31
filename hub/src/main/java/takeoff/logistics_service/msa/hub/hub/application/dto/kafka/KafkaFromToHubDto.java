package takeoff.logistics_service.msa.hub.hub.application.dto.kafka;

import java.util.List;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Builder
public record KafkaFromToHubDto(List<GetRouteResponseDto> fromToHubList) {

    public static KafkaFromToHubDto toKafka(List<GetRouteResponseDto> fromToHubList) {
        return KafkaFromToHubDto.builder()
            .fromToHubList(fromToHubList)
            .build();
    }
}

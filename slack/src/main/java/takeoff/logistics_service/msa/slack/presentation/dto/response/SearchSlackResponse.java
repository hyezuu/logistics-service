package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.SearchSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchSlackResponse(UUID slackId,
                                  Long userId,
                                  SearchContentsResponse searchContentsResponse) {

    public static SearchSlackResponse from(SearchSlackResponseDto responseDto) {
        return SearchSlackResponse.builder()
            .slackId(responseDto.slackId())
            .userId(responseDto.userId())
            .searchContentsResponse(SearchContentsResponse.from(responseDto.searchContentsResponseDto()))
            .build();
    }


}

package takeoff.logistics_service.msa.slack.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.domain.repository.search.SlackSearchCriteriaResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchSlackResponseDto(UUID slackId,
                                     Long userId,
                                     SearchContentsResponseDto searchContentsResponseDto) {

    public static SearchSlackResponseDto from(SlackSearchCriteriaResponse slackSearchCriteriaResponse) {
        return SearchSlackResponseDto.builder()
            .slackId(slackSearchCriteriaResponse.slackId())
            .userId(slackSearchCriteriaResponse.userId())
            .searchContentsResponseDto(SearchContentsResponseDto.from(slackSearchCriteriaResponse.contents()))
            .build();
    }

}

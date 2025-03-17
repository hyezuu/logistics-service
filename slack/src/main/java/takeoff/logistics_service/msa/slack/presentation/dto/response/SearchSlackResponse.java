package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchSlackResponse(UUID slackId,
                                  UUID userId,
                                  SearchContentsResponse searchContentsResponse) {


    public static SearchSlackResponse from(Slack slack) {
        return SearchSlackResponse.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .searchContentsResponseDto(SearchContentsResponse.from(slack.getContents()))
            .build();
    }


}

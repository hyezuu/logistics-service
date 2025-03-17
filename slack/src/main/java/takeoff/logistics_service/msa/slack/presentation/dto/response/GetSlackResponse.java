package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetSlackResponse(UUID slackId,
                               UUID userId,
                               GetContentsResponse getContentsResponse) {


    public static GetSlackResponse from(Slack slack) {
        return GetSlackResponse.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .getContentsResponseDto(GetContentsResponse.from(slack.getContents()))
            .build();
    }


}

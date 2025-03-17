package takeoff.logistics_service.msa.slack.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PostSlackResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Builder
public record PostSlackResponseDto(UUID slackId,
                                   Long userId,
                                   PostContentsResponseDto postContentsResponseDto) {

    public static PostSlackResponseDto from(PostSlackResponse postSlackResponse) {
        return PostSlackResponseDto.builder()
            .slackId(postSlackResponse.slackId())
            .userId(postSlackResponse.userId())
            .postContentsResponseDto(PostContentsResponseDto.from(postSlackResponse.postContentsResponse()))
            .build();
    }

    public static PostSlackResponseDto from(Slack slack) {
        return PostSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getUserId())
            .postContentsResponseDto(PostContentsResponseDto.from(slack.getContents()))
            .build();
    }


}

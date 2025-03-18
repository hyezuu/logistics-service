package takeoff.logistics_service.msa.slack.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.domain.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Builder
public record PostSlackResponseDto(UUID slackId,
                                   Long userId,
                                   PostContentsResponseDto postContentsResponseDto) {


    public static PostSlackResponseDto from(Slack slack) {
        return PostSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getUserId())
            .postContentsResponseDto(PostContentsResponseDto.from(slack.getContents()))
            .build();
    }


}

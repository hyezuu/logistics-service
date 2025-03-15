package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PostSlackResponseDto(UUID slackId,
                                   UUID userId,
                                   PostContentsResponseDto postContentsResponseDto) {


    public static PostSlackResponseDto from(Slack slack, PostContentsResponseDto postContentsResponseDto) {
        return PostSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .postContentsResponseDto(postContentsResponseDto)
            .build();
    }


}

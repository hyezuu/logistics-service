package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SlackResponseDto(UUID slackId,
                               UUID userId,
                               ContentsResponseDto contentsResponseDto) {


    public static SlackResponseDto from(Slack slack, ContentsResponseDto contentsResponseDto) {
        return SlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .contentsResponseDto(contentsResponseDto)
            .build();
    }
    public static SlackResponseDto from(Slack slack) {
        return SlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .contentsResponseDto(ContentsResponseDto.from(slack.getContents()))
            .build();
    }


}

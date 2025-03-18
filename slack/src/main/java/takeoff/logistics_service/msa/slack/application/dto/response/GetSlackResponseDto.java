package takeoff.logistics_service.msa.slack.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.domain.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetSlackResponseDto(UUID slackId,
                                  Long userId,
                                  GetContentsResponseDto getContentsResponseDto) {

    public static GetSlackResponseDto from(Slack slack) {
        return GetSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getUserId())
            .getContentsResponseDto(GetContentsResponseDto.from(slack.getContents()))
            .build();
    }

}

package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetSlackResponseDto(UUID slackId,
                                  UUID userId,
                                  GetContentsResponseDto getContentsResponseDto) {


    public static GetSlackResponseDto from(Slack slack) {
        return GetSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .getContentsResponseDto(GetContentsResponseDto.from(slack.getContents()))
            .build();
    }


}

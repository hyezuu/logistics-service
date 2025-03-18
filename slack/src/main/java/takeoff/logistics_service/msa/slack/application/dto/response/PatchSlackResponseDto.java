package takeoff.logistics_service.msa.slack.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.domain.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PatchSlackResponseDto(UUID slackId,
                                    Long userId,
                                    PatchContentsResponseDto patchContentsResponseDto) {

    public static PatchSlackResponseDto from(Slack slack) {
        return PatchSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getUserId())
            .patchContentsResponseDto(PatchContentsResponseDto.from(slack.getContents()))
            .build();
    }

}

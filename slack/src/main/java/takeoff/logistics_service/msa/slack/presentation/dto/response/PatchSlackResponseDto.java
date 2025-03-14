package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PatchSlackResponseDto(UUID slackId,
                                    UUID userId,
                                    PatchContentsResponseDto patchContentsResponseDto) {


    public static PatchSlackResponseDto from(Slack slack) {
        return PatchSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .patchContentsResponseDto(PatchContentsResponseDto.from(slack.getContents()))
            .build();
    }


}

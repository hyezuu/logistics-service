package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PatchSlackResponse(UUID slackId,
                                 UUID userId,
                                 PatchContentsResponse patchContentsResponse) {


    public static PatchSlackResponse from(Slack slack) {
        return PatchSlackResponse.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .patchContentsResponseDto(PatchContentsResponse.from(slack.getContents()))
            .build();
    }


}

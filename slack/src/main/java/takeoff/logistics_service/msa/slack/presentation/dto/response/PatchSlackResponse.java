package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.PatchSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PatchSlackResponse(UUID slackId,
                                 Long userId,
                                 PatchContentsResponse patchContentsResponse) {


    public static PatchSlackResponse from(PatchSlackResponseDto patchSlackResponseDto) {
        return PatchSlackResponse.builder()
            .slackId(patchSlackResponseDto.slackId())
            .userId(patchSlackResponseDto.userId())
            .patchContentsResponse(PatchContentsResponse.from(patchSlackResponseDto.patchContentsResponseDto()))
            .build();
    }


}

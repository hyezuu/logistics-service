package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.GetSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetSlackResponse(UUID slackId,
                               Long userId,
                               GetContentsResponse getContentsResponse) {

    public static GetSlackResponse from(GetSlackResponseDto getSlackResponseDto) {
        return GetSlackResponse.builder()
            .slackId(getSlackResponseDto.slackId())
            .userId(getSlackResponseDto.userId())
            .getContentsResponse(GetContentsResponse.from(getSlackResponseDto.getContentsResponseDto()))
            .build();
    }


}

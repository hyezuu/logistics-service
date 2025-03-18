package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.PostSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PostSlackResponse(UUID slackId,
                                Long userId,
                                PostContentsResponse postContentsResponse) {

    public static PostSlackResponse from(PostSlackResponseDto postSlackResponseDto) {
        return PostSlackResponse.builder()
            .slackId(postSlackResponseDto.slackId())
            .userId(postSlackResponseDto.userId())
            .postContentsResponse(PostContentsResponse.from(postSlackResponseDto.postContentsResponseDto()))
            .build();
    }


}

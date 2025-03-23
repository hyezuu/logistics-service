package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.PostContentsResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PostContentsResponse(String message,
                                   LocalDateTime sentAt) {

    public static PostContentsResponse from(PostContentsResponseDto postContentsResponseDto) {
        return PostContentsResponse.builder()
            .message(postContentsResponseDto.message())
            .sentAt(postContentsResponseDto.sentAt())
            .build();
    }

}

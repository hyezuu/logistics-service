package takeoff.logistics_service.msa.slack.application.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Contents;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PostContentsResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Builder
public record PostContentsResponseDto(String message,
                                      LocalDateTime sent_At) {

    public static PostContentsResponseDto from(PostContentsResponse postContentsResponse) {
        return PostContentsResponseDto.builder()
            .message(postContentsResponse.message())
            .sent_At(postContentsResponse.sent_At())
            .build();
    }

    public static PostContentsResponseDto from(Contents contents) {
        return PostContentsResponseDto.builder()
            .message(contents.getMessage())
            .sent_At(contents.getSentAt())
            .build();
    }

}

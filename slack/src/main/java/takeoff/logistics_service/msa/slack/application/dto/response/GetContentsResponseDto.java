package takeoff.logistics_service.msa.slack.application.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.domain.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetContentsResponseDto(String message,
                                     LocalDateTime sentAt) {


    public static GetContentsResponseDto from(Contents contents) {
        return GetContentsResponseDto.builder()
            .message(contents.getMessage())
            .sentAt(contents.getSentAt())
            .build();
    }
}

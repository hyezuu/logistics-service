package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetContentsResponseDto(String message,
                                     LocalDateTime sent_At) {

    public static GetContentsResponseDto from(Contents contents) {
        return GetContentsResponseDto.builder()
            .message(contents.getMessage())
            .sent_At(contents.getSentAt())
            .build();
    }

}

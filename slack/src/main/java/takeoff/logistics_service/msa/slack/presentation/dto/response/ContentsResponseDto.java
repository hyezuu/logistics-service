package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record ContentsResponseDto(String message,
                                  LocalDateTime sent_At) {

    public static ContentsResponseDto from(Contents contents) {
        return ContentsResponseDto.builder()
            .message(contents.getMessage())
            .sent_At(contents.getSent_At())
            .build();
    }

}

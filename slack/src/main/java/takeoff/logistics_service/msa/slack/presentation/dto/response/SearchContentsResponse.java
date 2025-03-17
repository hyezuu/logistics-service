package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchContentsResponse(String message,
                                     LocalDateTime sentAt) {

    public static SearchContentsResponse from(Contents contents) {
        return SearchContentsResponse.builder()
            .message(contents.getMessage())
            .sentAt(contents.getSentAt())
            .build();
    }

}

package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.SearchContentsResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchContentsResponse(String message,
                                     LocalDateTime sentAt) {

    public static SearchContentsResponse from(SearchContentsResponseDto searchContentsResponseDto) {
        return SearchContentsResponse.builder()
            .message(searchContentsResponseDto.message())
            .sentAt(searchContentsResponseDto.sentAt())
            .build();
    }

}

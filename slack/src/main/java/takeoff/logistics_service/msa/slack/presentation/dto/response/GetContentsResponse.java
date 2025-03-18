package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.GetContentsResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetContentsResponse(String message,
                                  LocalDateTime sent_At) {

    public static GetContentsResponse from(GetContentsResponseDto getContentsResponseDto) {
        return GetContentsResponse.builder()
            .message(getContentsResponseDto.message())
            .sent_At(getContentsResponseDto.sentAt())
            .build();
    }

}

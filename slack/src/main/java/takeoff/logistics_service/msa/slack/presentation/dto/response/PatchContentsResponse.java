package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.PatchContentsResponseDto;
import takeoff.logistics_service.msa.slack.domain.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PatchContentsResponse(String message,
                                    LocalDateTime sentAt) {

    public static PatchContentsResponse from(PatchContentsResponseDto patchContentsResponseDto) {
        return PatchContentsResponse.builder()
            .message(patchContentsResponseDto.message())
            .sentAt(patchContentsResponseDto.sentAt())
            .build();
    }
    public static PatchContentsResponseDto from(Contents contents) {
        return PatchContentsResponseDto.builder()
            .message(contents.getMessage())
            .sentAt(contents.getSentAt())
            .build();
    }

}

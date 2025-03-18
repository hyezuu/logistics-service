package takeoff.logistics_service.msa.slack.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.application.dto.request.PatchContentsRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public record PatchContentsRequest(@NotNull String message) {

    public static PatchContentsRequestDto from(PatchContentsRequest patchContentsRequest) {
        return PatchContentsRequestDto.builder()
            .message(patchContentsRequest.message())
            .build();
    }

}

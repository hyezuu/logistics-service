package takeoff.logistics_service.msa.slack.presentation.dto.request;


import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.application.dto.request.PatchSlackRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record PatchSlackRequest(@NotNull Long userId,
                                @NotNull PatchContentsRequest patchContentsRequest) {

    public static PatchSlackRequestDto toApplicationDto(PatchSlackRequest request, Long userId) {
        return PatchSlackRequestDto.builder()
            .userId(userId)
            .patchContentsRequestDto(request.patchContentsRequest.toApplicationDto())
            .build();
    }

}

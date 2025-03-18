package takeoff.logistics_service.msa.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record GetUserRequestDto(
        @NotNull(message = "사용자 ID는 필수입니다.")
        Long userId
) {
}

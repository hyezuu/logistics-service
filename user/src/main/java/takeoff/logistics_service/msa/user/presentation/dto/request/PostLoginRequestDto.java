package takeoff.logistics_service.msa.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostLoginRequestDto(
        @NotBlank(message = "사용자 이름을 입력해주세요.")
        String username,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}

package takeoff.logistics_service.msa.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.vo.SlackId;

@Builder
public record PatchUserRequestDto(
        @NotBlank(message = "새로운 사용자명은 필수 입력 항목입니다.")
        String username,

        @NotBlank(message = "새로운 이메일은 필수 입력 항목입니다.")
        @Email(message = "유효한 이메일 형식을 입력해주세요.")
        String slackEmail
) {
}

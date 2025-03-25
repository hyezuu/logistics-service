package takeoff.logistics_service.msa.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import takeoff.logistics_service.msa.user.application.dto.GetCompanyFeignResponse;
import takeoff.logistics_service.msa.user.application.dto.GetHubFeignResponse;
import takeoff.logistics_service.msa.user.domain.entity.CompanyManager;
import takeoff.logistics_service.msa.user.domain.entity.HubManager;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;

@Builder
public record PostSignupRequestDto(
        @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
        @Size(min = 4, max = 10, message = "사용자 이름은 4자 이상, 10자 이하로 입력해주세요.")
        @Pattern(regexp = "^[a-z0-9]+$", message = "사용자 이름은 소문자 및 숫자만 가능합니다.")
        String username,

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효한 이메일 형식을 입력해주세요.")
        String slackEmail,

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다.")
        String password,
        UserRole role,
        GetCompanyFeignResponse companyFeignResponse,
        GetHubFeignResponse hubFeignResponse
) {
    public User toEntity(String encodedPassword){
        return switch (role) {
            case COMPANY_MANAGER -> CompanyManager.createFromFeign(
                    username, slackEmail, encodedPassword, role, companyFeignResponse
            );
            case HUB_MANAGER -> HubManager.createFromFeign(
                    username, slackEmail, encodedPassword, role, hubFeignResponse
            );
            default -> User.create(username, slackEmail, encodedPassword, role);
        };
    }

}

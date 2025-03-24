package takeoff.logistics_service.msa.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;

@Builder
public record PostDeliveryManagerRequestDto(
    @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
        String username,

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효한 이메일 형식을 입력해주세요.")
        String slackEmail,

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        String password,

    @NotNull(message = "사용자 역할은 필수 입력 항목입니다.")
        UserRole role,

    @NotNull(message = "배송 관리자 타입은 필수 입력 항목입니다.")
        DeliveryManagerType deliveryManagerType,


    UUID identifier, // HubId 또는 CompanyId

    Integer deliverySequence
) {
    public DeliveryManager toEntityWithSequence(String encodePassword,UUID identifier, PostDeliveryManagerRequestDto requestDto) {
        return DeliveryManager.create(
                username,
                slackEmail,
                encodePassword,
                role,
                identifier,
                DeliverySequence.from(requestDto.deliverySequence()),
                deliveryManagerType
        );
    }
}

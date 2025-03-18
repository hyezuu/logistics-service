package takeoff.logistics_service.msa.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;

@Builder
public record PatchDeliveryManagerRequestDto(
        @Email(message = "유효한 이메일 형식을 입력해주세요.")
        String slackEmail,

        @NotNull(message = "배송 순번은 필수 입력 항목입니다.")
        @Min(value = 1, message = "배송 순번은 1 이상이어야 합니다.")
        Integer deliverySequence,

        String hubId,
        String companyId
) {
}

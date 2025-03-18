package takeoff.logistics_service.msa.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DeleteDeliveryManagerRequestDto(
        @NotNull(message = "삭제할 배송 관리자 ID는 필수 입력 항목입니다.")
        Long userId
) {
}

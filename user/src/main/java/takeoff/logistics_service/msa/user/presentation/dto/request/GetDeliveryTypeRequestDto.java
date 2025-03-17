package takeoff.logistics_service.msa.user.presentation.dto.request;

import lombok.Builder;

@Builder
public record GetDeliveryTypeRequestDto(Long userId) {
}

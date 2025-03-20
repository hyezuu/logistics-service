package takeoff.logistics_service.msa.user.presentation.dto.request;

import lombok.Builder;

@Builder
public record UserValidationRequestDto(
        String username,
        String password
) {
}

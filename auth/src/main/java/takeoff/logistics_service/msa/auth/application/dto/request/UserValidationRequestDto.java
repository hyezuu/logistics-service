package takeoff.logistics_service.msa.auth.application.dto.request;

import lombok.Builder;

@Builder
public record UserValidationRequestDto(
        String username,
        String password
) {
}

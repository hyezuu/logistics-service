package takeoff.logistics_service.msa.auth.application.dto.response;

import lombok.Builder;


@Builder
public record UserValidationResponseDto(
        String userId,
        String username,
        String role
) {
}

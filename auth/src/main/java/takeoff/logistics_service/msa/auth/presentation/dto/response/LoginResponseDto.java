package takeoff.logistics_service.msa.auth.presentation.dto.response;

import lombok.Builder;

@Builder
public record LoginResponseDto(
        String accessToken,
        String refreshToken,
        String message
) {}

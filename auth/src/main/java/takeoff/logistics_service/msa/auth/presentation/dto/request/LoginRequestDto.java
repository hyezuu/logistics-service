package takeoff.logistics_service.msa.auth.presentation.dto.request;

import lombok.Builder;

@Builder
public record LoginRequestDto(
        String username,
        String password
) {}

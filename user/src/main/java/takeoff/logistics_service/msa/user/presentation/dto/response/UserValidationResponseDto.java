package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.Optional;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record UserValidationResponseDto(
        String userId,
        String username,
        String role
) {
    public static UserValidationResponseDto from(User user) {
        return UserValidationResponseDto.builder()
                .userId(Optional.ofNullable(user.getId()).map(id -> id.toString()).orElse(null))
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}

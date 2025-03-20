package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.vo.UserId;

@Builder
public record UserValidationResponseDto(
        UserId userId,
        String username,
        String role
) {
    public static UserValidationResponseDto from(User user) {
        return UserValidationResponseDto.builder()
                .userId(UserId.from(user.getId()))
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}

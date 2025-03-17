package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record GetUserResponseDto(
        Long userId,
        String username,
        String email,
        String role,
        String slackId
) {
    public static GetUserResponseDto from(User user) {
        return GetUserResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .slackId(user.getSlackId().getValue().toString())
                .build();
    }
}

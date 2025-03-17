package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record PatchUserResponseDto(
        Long userId,
        String username,
        String email,
        String role,
        String slackId
) {
    public static PatchUserResponseDto from(User user) {
        return PatchUserResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .slackId(user.getSlackId().getValue().toString())
                .build();
    }
}
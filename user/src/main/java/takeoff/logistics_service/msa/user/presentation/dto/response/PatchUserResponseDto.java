package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record PatchUserResponseDto(
        Long userId,
        String username,
        String slackEmail,
        String role
) {
    public static PatchUserResponseDto from(User user) {
        return PatchUserResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .slackEmail(user.getSlackEmail())
                .role(user.getRole().name())
                .build();
    }
}
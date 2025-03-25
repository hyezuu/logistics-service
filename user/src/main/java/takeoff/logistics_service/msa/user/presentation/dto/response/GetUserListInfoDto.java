package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record GetUserListInfoDto(
        Long userId,
        String username,
        String slackEmail,
        String role,
        UUID companyId,
        UUID hubId
) {
    public static GetUserListInfoDto from(User user) {
        return GetUserListInfoDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .slackEmail(user.getSlackEmail())
                .role(user.getRole().name())
                .build();
    }
}

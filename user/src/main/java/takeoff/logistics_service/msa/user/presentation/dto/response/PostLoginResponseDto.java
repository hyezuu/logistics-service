package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record PostLoginResponseDto(
        Long userId,
        String username,
        String accessToken,
        String refreshToken
) {
    public static PostLoginResponseDto from(User user, String accessToken, String refreshToken){
        return PostLoginResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

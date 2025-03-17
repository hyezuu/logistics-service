package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.vo.SlackId;

import java.util.UUID;

@Builder
public record PostSignupResponseDto(Long userId,
                                String username,
                                String email,
                                UUID slackId) {

    public static PostSignupResponseDto from(User user) {
        return PostSignupResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .slackId(user.getSlackId().getValue())
                .build();
    }
}

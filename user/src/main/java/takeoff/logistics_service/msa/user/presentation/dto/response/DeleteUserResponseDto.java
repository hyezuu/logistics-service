package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record DeleteUserResponseDto(
        Long userId,
        LocalDateTime deletedAt,
        String message
) {
    public static DeleteUserResponseDto from(User user) {
        return DeleteUserResponseDto.builder()
                .userId(user.getId())
                .deletedAt(LocalDateTime.now())
                .message("사용자 계정 삭제(논리 삭제)가 완료되었습니다.")
                .build();
    }
}

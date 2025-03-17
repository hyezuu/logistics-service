package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record DeleteUserResponseDto(String message) {

    public static DeleteUserResponseDto from(User user) {
        return DeleteUserResponseDto.builder()
                .message("사용자 계정 삭제 완료")
                .build();
    }
}

package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DeleteDeliveryManagerResponseDto(
        Long userId,
        LocalDateTime deletedAt,
        String message
) {
    public static DeleteDeliveryManagerResponseDto from(Long id) {
        return DeleteDeliveryManagerResponseDto.builder()
                .userId(id)
                .deletedAt(LocalDateTime.now())
                .message("배송 관리자 삭제(논리 삭제)가 완료되었습니다.")
                .build();
    }
}

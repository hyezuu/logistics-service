package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;

@Builder
public record PostDeliveryManagerResponseDto(
        Long userId,
        String username,
        String slackEmail,
        DeliveryManagerType deliveryManagerType,
        String identifier,
        int deliverySequence
) {
    public static PostDeliveryManagerResponseDto from(DeliveryManager manager) {
        return PostDeliveryManagerResponseDto.builder()
                .userId(manager.getId())
                .username(manager.getUsername())
                .slackEmail(manager.getSlackEmail())
                .deliveryManagerType(manager.getDeliveryManagerType())
                .identifier(manager.getIdentifier())
                .deliverySequence(manager.getDeliverySequence().getSequenceNumber())
                .build();
    }
}

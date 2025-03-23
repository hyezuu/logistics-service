package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;

@Builder
public record GetDeliveryManagerResponseDto(
        Long userId,
        String username,
        String slackEmail,
        DeliveryManagerType deliveryManagerType,
        String identifier,
        int deliverySequence
) {
    public static GetDeliveryManagerResponseDto from(DeliveryManager manager) {
        return GetDeliveryManagerResponseDto.builder()
                .userId(manager.getId())
                .username(manager.getUsername())
                .slackEmail(manager.getSlackEmail())
                .deliveryManagerType(manager.getDeliveryManagerType())
                .identifier(manager.getIdentifier())
                .deliverySequence(manager.getDeliverySequence().getSequenceNumber())
                .build();
    }
}

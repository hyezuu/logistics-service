package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;

@Builder
public record PatchDeliveryManagerResponseDto(
        Long userId,
        String username,
        String slackEmail,
        DeliveryManagerType deliveryManagerType,
        String identifier,
        int deliverySequence
) {
    public static PatchDeliveryManagerResponseDto from(DeliveryManager manager) {
        return PatchDeliveryManagerResponseDto.builder()
                .userId(manager.getId())
                .username(manager.getUsername())
                .slackEmail(manager.getSlackEmail())
                .deliveryManagerType(manager.getDeliveryManagerType())
                .identifier(manager.getIdentifier())
                .deliverySequence(manager.getDeliverySequence().getSequenceNumber())
                .build();
    }
}

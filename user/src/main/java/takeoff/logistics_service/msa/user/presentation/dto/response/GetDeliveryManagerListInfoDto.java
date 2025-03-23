package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;

@Builder
public record GetDeliveryManagerListInfoDto(
        Long deliveryManagerId,
        String username,
        String slackEmail,
        String deliveryManagerType,
        String identifier,
        int sequenceNumber
) {
    public static GetDeliveryManagerListInfoDto from(DeliveryManager manager) {
        return GetDeliveryManagerListInfoDto.builder()
                .deliveryManagerId(manager.getId())
                .username(manager.getUsername())
                .slackEmail(manager.getSlackEmail())
                .deliveryManagerType(manager.getDeliveryManagerType().name())
                .identifier(manager.getIdentifier())
                .sequenceNumber(manager.getDeliverySequence().getSequenceNumber())
                .build();
    }
}

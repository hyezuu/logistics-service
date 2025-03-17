package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.CompanyDeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.HubDeliveryManager;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record GetDeliveryTypeResponseDto(
        Long userId,
        String deliveryType,
        String hubIdentifier,
        String companyIdentifier
) {
    public static GetDeliveryTypeResponseDto from(DeliveryManager deliveryManager) {
        return GetDeliveryTypeResponseDto.builder()
                .userId(deliveryManager.getId())
                .deliveryType(deliveryManager.getDeliveryManagerType().name())
                .hubIdentifier(deliveryManager.isHubDeliveryManager() ? deliveryManager.getHubIdentifier() : null)
                .companyIdentifier(deliveryManager.isCompanyDeliveryManager() ? deliveryManager.getCompanyIdentifier() : null)
                .build();
    }
}

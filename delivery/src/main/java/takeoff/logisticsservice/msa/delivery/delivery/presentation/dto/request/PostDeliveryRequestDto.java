package takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request;

import java.util.UUID;

public record PostDeliveryRequestDto(UUID orderID, Long customerId, UUID fromHubId, UUID toHubId) {
}

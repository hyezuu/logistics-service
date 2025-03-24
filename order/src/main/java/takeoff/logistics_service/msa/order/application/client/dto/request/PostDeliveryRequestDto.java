package takeoff.logistics_service.msa.order.application.client.dto.request;

import java.util.UUID;

public record PostDeliveryRequestDto(UUID orderID, Long customerId, UUID fromHubId, UUID toHubId) {
}

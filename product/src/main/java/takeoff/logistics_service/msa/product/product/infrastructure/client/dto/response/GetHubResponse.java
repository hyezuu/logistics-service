package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response;

import java.util.UUID;

public record GetHubResponse(
	UUID hubId, String hubName, String address, Double latitude, Double longitude) {
}

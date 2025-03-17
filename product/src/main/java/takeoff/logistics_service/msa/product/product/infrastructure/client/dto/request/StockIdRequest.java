package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request;

import java.util.UUID;

public record StockIdRequest(UUID productId, UUID hubId) {

	public static StockIdRequest from(UUID productId, UUID hubId) {
		return new StockIdRequest(productId, hubId);
	}
}

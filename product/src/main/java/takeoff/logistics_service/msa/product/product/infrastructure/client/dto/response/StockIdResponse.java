package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response;

import java.util.UUID;

public record StockIdResponse(UUID productId, UUID hubId) {

	public static StockIdResponse from(UUID productId, UUID hubId) {
		return new StockIdResponse(productId, hubId);
	}
}

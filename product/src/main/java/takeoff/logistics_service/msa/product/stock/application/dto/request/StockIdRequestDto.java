package takeoff.logistics_service.msa.product.stock.application.dto.request;

import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.domain.entity.CreateStockId;

public record StockIdRequestDto(UUID productId, UUID hubId) {

	public CreateStockId toCommand() {
		return new CreateStockId(productId, hubId);
	}
}

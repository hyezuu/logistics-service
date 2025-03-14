package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;

public record PostStockRequestDto(@NotNull StockIdDto stockId, @Positive Integer quantity) {

	public Stock toEntity() {
		return Stock.builder()
			.id(stockId.toVo())
			.quantity(quantity)
			.build();
	}
}

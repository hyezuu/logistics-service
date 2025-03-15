package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;

@Builder
public record IncreaseStockResponseDto(StockIdDto stockId, Integer quantity) {

	public static IncreaseStockResponseDto from(Stock stock) {
		return IncreaseStockResponseDto.builder()
			.stockId(StockIdDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.build();
	}
}

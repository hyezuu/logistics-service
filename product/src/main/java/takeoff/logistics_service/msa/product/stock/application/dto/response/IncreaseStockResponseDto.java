package takeoff.logistics_service.msa.product.stock.application.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;

@Builder
public record IncreaseStockResponseDto(StockIdResponseDto stockId, Integer quantity) {

	public static IncreaseStockResponseDto from(Stock stock) {
		return IncreaseStockResponseDto.builder()
			.stockId(StockIdResponseDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.build();
	}
}

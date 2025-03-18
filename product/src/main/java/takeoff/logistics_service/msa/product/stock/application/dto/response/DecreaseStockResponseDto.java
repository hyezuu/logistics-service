package takeoff.logistics_service.msa.product.stock.application.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;

@Builder
public record DecreaseStockResponseDto(StockIdResponseDto stockId, Integer quantity) {

	public static DecreaseStockResponseDto from(Stock stock) {
		return DecreaseStockResponseDto.builder()
			.stockId(StockIdResponseDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.build();
	}
}

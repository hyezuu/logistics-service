package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;

@Builder
public record PostStockResponseDto(StockIdDto stockId, Integer quantity) {

	public static PostStockResponseDto from(Stock stock) {
		return PostStockResponseDto.builder()
			.stockId(StockIdDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.build();
	}
}

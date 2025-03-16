package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;

@Builder
public record GetStockResponseDto(StockIdDto stockId, Integer quantity, LocalDateTime updatedAt) {

	public static GetStockResponseDto from(Stock stock) {
		return GetStockResponseDto.builder()
			.stockId(StockIdDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.updatedAt(stock.getUpdatedAt())
			.build();
	}
}
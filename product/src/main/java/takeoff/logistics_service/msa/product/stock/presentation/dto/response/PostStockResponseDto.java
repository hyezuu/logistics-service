package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;

@Builder
public record PostStockResponseDto(StockIdDto stockId, Integer quantity, LocalDateTime createdAt) {

	public static PostStockResponseDto from(Stock stock) {
		return PostStockResponseDto.builder()
			.stockId(StockIdDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.createdAt(stock.getCreatedAt())
			.build();
	}
}

package takeoff.logistics_service.msa.product.stock.application.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;

@Builder
public record PostStockResponseDto(StockIdResponseDto stockId, Integer quantity, LocalDateTime createdAt) {

	public static PostStockResponseDto from(Stock stock) {
		return PostStockResponseDto.builder()
			.stockId(StockIdResponseDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.createdAt(stock.getCreatedAt())
			.build();
	}
}

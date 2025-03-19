package takeoff.logistics_service.msa.product.stock.application.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteriaResponse;

@Builder
public record GetStockResponseDto(StockIdResponseDto stockId, Integer quantity, LocalDateTime updatedAt) {

	public static GetStockResponseDto from(Stock stock) {
		return GetStockResponseDto.builder()
			.stockId(StockIdResponseDto.from(stock.getId()))
			.quantity(stock.getQuantity())
			.updatedAt(stock.getUpdatedAt())
			.build();
	}

	public static GetStockResponseDto from(StockSearchCriteriaResponse response) {
		return GetStockResponseDto.builder()
			.stockId(StockIdResponseDto.from(response.stockId()))
			.quantity(response.quantity())
			.updatedAt(response.updatedAt())
			.build();
	}
}
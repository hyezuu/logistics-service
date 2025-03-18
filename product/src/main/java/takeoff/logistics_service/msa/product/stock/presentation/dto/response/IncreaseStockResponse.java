package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.application.dto.response.IncreaseStockResponseDto;

@Builder
public record IncreaseStockResponse(StockIdResponse stockId, Integer quantity) {

	public static IncreaseStockResponse from(IncreaseStockResponseDto responseDto) {
		return IncreaseStockResponse.builder()
			.stockId(StockIdResponse.from(responseDto.stockId()))
			.quantity(responseDto.quantity())
			.build();
	}
}

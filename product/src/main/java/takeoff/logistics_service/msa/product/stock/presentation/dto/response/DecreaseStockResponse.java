package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.application.dto.response.DecreaseStockResponseDto;

@Builder
public record DecreaseStockResponse(StockIdResponse stockId, Integer quantity) {

	public static DecreaseStockResponse from(DecreaseStockResponseDto responseDto) {
		return DecreaseStockResponse.builder()
			.stockId(StockIdResponse.from(responseDto.stockId()))
			.quantity(responseDto.quantity())
			.build();
	}
}

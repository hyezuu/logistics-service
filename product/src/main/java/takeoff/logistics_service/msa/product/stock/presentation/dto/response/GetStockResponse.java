package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetStockResponseDto;

@Builder
public record GetStockResponse(StockIdResponse stockId, Integer quantity, LocalDateTime updatedAt) {

	public static GetStockResponse from(GetStockResponseDto responseDto) {
		return GetStockResponse.builder()
			.stockId(StockIdResponse.from(responseDto.stockId()))
			.quantity(responseDto.quantity())
			.updatedAt(responseDto.updatedAt())
			.build();
	}
}
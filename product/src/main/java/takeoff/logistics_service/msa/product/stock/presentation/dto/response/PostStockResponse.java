package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.application.dto.response.PostStockResponseDto;

@Builder
public record PostStockResponse(StockIdResponse stockId, Integer quantity, LocalDateTime createdAt) {

	public static PostStockResponse from(PostStockResponseDto responseDto) {
		return PostStockResponse.builder()
			.stockId(StockIdResponse.from(responseDto.stockId()))
			.quantity(responseDto.quantity())
			.createdAt(responseDto.createdAt())
			.build();
	}
}

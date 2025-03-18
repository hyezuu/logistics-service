package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response;

import java.time.LocalDateTime;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;

public record PostStockResponse(StockIdResponse stockId, Integer quantity, LocalDateTime createdAt) {

	public PostStockResponseDto toApplicationDto(){
		return PostStockResponseDto.builder()
			.productId(stockId.productId())
			.hubId(stockId.hubId())
			.quantity(quantity)
			.createdAt(createdAt).build();
	}
}

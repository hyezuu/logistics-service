package takeoff.logistics_service.msa.product.stock.presentation.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.application.dto.response.StockIdResponseDto;

public record StockIdResponse(UUID productId, UUID hubId) {

	public static StockIdResponse from(StockIdResponseDto responseDto) {
		return new StockIdResponse(responseDto.productId(), responseDto.hubId());
	}
}

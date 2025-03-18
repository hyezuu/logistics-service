package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request;

import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;

public record PostStockRequest(StockIdRequest stockId, Integer quantity) {

	public static PostStockRequest from(PostStockRequestDto requestDto) {
		return new PostStockRequest(
			StockIdRequest
				.from(requestDto.productId(), requestDto.hubId()), requestDto.quantity());
	}
}


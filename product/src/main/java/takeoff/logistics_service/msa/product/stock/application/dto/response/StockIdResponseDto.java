package takeoff.logistics_service.msa.product.stock.application.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockIdSearchCriteriaResponse;

public record StockIdResponseDto(UUID productId, UUID hubId) {

	public static StockIdResponseDto from(StockId stockId) {
		return new StockIdResponseDto(stockId.getProductId(), stockId.getHubId());
	}

	public static StockIdResponseDto from(StockIdSearchCriteriaResponse response) {
		return new StockIdResponseDto(response.productId(), response.hubId());
	}
}


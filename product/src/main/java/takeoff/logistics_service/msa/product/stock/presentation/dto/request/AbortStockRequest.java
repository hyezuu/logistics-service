package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import java.util.List;
import takeoff.logistics_service.msa.product.stock.application.dto.request.AbortStockRequestDto;

public record AbortStockRequest(List<StockItemRequest> stocks) {

	public AbortStockRequestDto toApplicationDto() {
		return new AbortStockRequestDto(
			stocks.stream().map(StockItemRequest::toApplicationDto).toList());
	}
}
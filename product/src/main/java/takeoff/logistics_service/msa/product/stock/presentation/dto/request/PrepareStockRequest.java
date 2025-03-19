package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import java.util.List;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PrepareStockRequestDto;

public record PrepareStockRequest(List<StockItemRequest> stocks) {

	public PrepareStockRequestDto toApplicationDto() {
		return new PrepareStockRequestDto(
			stocks.stream().map(StockItemRequest::toApplicationDto).toList());
	}
}

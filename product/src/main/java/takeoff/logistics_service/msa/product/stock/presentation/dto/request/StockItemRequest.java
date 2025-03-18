package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockItemRequestDto;

public record StockItemRequest(@NotNull StockIdRequest stockId, @Positive Integer quantity) {

	public StockItemRequestDto toApplicationDto() {
		return new StockItemRequestDto(stockId.toApplicationDto(), quantity);
	}
}

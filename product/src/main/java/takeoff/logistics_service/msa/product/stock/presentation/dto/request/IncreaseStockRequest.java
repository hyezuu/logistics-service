package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import takeoff.logistics_service.msa.product.stock.application.dto.request.IncreaseStockRequestDto;

public record IncreaseStockRequest(@NotNull StockIdRequest stockId, @Positive Integer quantity) {

	public IncreaseStockRequestDto toApplicationDto() {
		return new IncreaseStockRequestDto(stockId.toApplicationDto(), quantity);
	}
}

package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import takeoff.logistics_service.msa.product.stock.application.dto.request.DecreaseStockRequestDto;

public record DecreaseStockRequest(@NotNull StockIdRequest stockId, @Positive Integer quantity) {

	public DecreaseStockRequestDto toApplicationDto() {
		return new DecreaseStockRequestDto(stockId.toApplicationDto(), quantity);
	}
}

package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PostStockRequestDto;

public record PostStockRequest(@NotNull StockIdRequest stockId, @PositiveOrZero Integer quantity) {

	public PostStockRequestDto toApplicationDto(){
		return new PostStockRequestDto(stockId.toApplicationDto(), quantity);
	}
}

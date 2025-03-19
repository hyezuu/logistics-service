package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockIdRequestDto;

public record StockIdRequest(@NotNull UUID productId, @NotNull UUID hubId) {

	public StockIdRequestDto toApplicationDto() {
		return new StockIdRequestDto(productId, hubId);
	}
}

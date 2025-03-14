package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;

public record DecreaseStockRequestDto(@NotNull StockIdDto stockId, @Positive Integer quantity) {

}

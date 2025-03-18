package takeoff.logistics_service.msa.product.stock.application.dto.request;

import takeoff.logistics_service.msa.product.stock.domain.command.CreateStock;
import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;

public record PostStockRequestDto(StockIdRequestDto stockId, Integer quantity) {

	public CreateStock toCommand() {
		return new CreateStock(StockId.create(stockId.toCommand()), quantity);
	}
}

package takeoff.logistics_service.msa.product.stock.domain.command;

import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;

public record CreateStock(StockId stockId, Integer quantity) {
}

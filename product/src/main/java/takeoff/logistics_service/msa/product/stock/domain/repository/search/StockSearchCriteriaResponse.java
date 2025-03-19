package takeoff.logistics_service.msa.product.stock.domain.repository.search;

import java.time.LocalDateTime;

public record StockSearchCriteriaResponse(
	StockIdSearchCriteriaResponse stockId, Integer quantity, LocalDateTime updatedAt) {
}

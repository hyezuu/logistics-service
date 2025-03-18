package takeoff.logistics_service.msa.product.stock.domain.repository.search;

import java.util.UUID;

public record StockSearchCriteria(
	UUID productId, UUID hubId, Boolean isAsc, String sortBy, int page, int size) {
}

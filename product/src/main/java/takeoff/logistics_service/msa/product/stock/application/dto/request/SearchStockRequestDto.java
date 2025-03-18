package takeoff.logistics_service.msa.product.stock.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteria;

@Builder
public record SearchStockRequestDto(
	UUID productId, UUID hubId, Boolean isAsc, String sortBy, int page, int size) {

	public StockSearchCriteria toSearchCriteria() {
		return new StockSearchCriteria(productId, hubId, isAsc, sortBy, page, size);
	}
}
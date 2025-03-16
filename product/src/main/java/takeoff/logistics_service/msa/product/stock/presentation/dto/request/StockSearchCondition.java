package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import java.util.UUID;

public record StockSearchCondition(UUID productId,
								   UUID hubId,
								   Boolean isAsc,
								   String sortBy
								   ) {
	public StockSearchCondition {
		isAsc = isAsc != null ? isAsc : false;
		sortBy = sortBy != null ? sortBy : "createdAt";
	}
}
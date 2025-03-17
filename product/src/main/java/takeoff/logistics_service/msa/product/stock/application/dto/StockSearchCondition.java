package takeoff.logistics_service.msa.product.stock.application.dto;

import java.util.UUID;

public record StockSearchCondition(UUID productId,
								   UUID hubId,
								   Boolean isAsc,
								   String sortBy
//	,
//								   int page,
//								   int size
								   ) {
	public StockSearchCondition {
		isAsc = isAsc != null ? isAsc : false;
		sortBy = sortBy != null ? sortBy : "createdAt";
//		size = (page == 10 || page == 30 || page == 50) ? page : 10;
	}
	// domain 계층으로의 변환 필요
}
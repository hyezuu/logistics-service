package takeoff.logistics_service.msa.product.stock.application.dto;

import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.application.exception.StockBusinessException;
import takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode;

public record DeleteStockCondition(UUID productId, UUID hubId) {

	public DeleteStockCondition {
		if (productId == null && hubId == null) {
			throw StockBusinessException.from(StockErrorCode.INVALID_REQUEST);
		}
	}

	// 유효한 검색 조건인지 확인하는 편의 메서드
	public boolean hasValidCriteria() {
		return productId != null || hubId != null;
	}
}

package takeoff.logistics_service.msa.product.stock.infrastructure.persistence;

import takeoff.logistics_service.msa.product.stock.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteria;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteriaResponse;

public interface JpaStockRepositoryCustom {

	PaginatedResult<StockSearchCriteriaResponse> search(StockSearchCriteria criteria);
}

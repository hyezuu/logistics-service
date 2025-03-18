package takeoff.logistics_service.msa.product.stock.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.product.stock.application.dto.request.SearchStockRequestDto;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteria;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteriaResponse;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponse;

public interface JpaStockRepositoryCustom {

	PaginatedResult<StockSearchCriteriaResponse> search(StockSearchCriteria criteria);
}

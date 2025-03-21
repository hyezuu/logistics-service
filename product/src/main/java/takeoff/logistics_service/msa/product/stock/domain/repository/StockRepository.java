package takeoff.logistics_service.msa.product.stock.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;
import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteria;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteriaResponse;

public interface StockRepository {

	Stock save(Stock stock);

	Optional<Stock> findByIdAndDeletedAtIsNull(StockId id);

	Optional<Stock> findByIdWithLock(StockId id);

	PaginatedResult<StockSearchCriteriaResponse> search(StockSearchCriteria criteria);

	List<Stock> findAllById_ProductIdAndDeletedAtIsNull(UUID productId);

	List<Stock> findAllById_HubIdAndDeletedAtIsNull(UUID hubId);

	List<Stock> findAllById_ProductIdAndDeletedAtIsNullOrderByQuantityDesc(UUID productId);

	boolean existsById(StockId id);
}

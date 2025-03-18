package takeoff.logistics_service.msa.product.stock.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.product.stock.application.dto.StockSearchCondition;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.model.entity.StockId;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponseDto;

public interface StockRepository {

	Stock save(Stock stock);

	Optional<Stock> findByIdAndDeletedAtIsNull(StockId id);

	Optional<Stock> findByIdWithLock(StockId id);

	Page<GetStockResponseDto> search(StockSearchCondition condition, Pageable pageable);

	List<Stock> findAllById_ProductIdAndDeletedAtIsNull(UUID productId);

	List<Stock> findAllById_HubIdAndDeletedAtIsNull(UUID hubId);
}

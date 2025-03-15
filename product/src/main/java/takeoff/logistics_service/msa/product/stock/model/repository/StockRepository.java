package takeoff.logistics_service.msa.product.stock.model.repository;

import java.util.Optional;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.model.entity.StockId;

public interface StockRepository {

	Stock save(Stock stock);

	Optional<Stock> findById(StockId id);

	Optional<Stock> findByIdWithLock(StockId vo);
}

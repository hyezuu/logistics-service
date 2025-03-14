package takeoff.logistics_service.msa.product.stock.infrastructure.persistence;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.model.entity.StockId;
import takeoff.logistics_service.msa.product.stock.model.repository.StockRepository;

public interface JpaStockRepository
	extends JpaRepository<Stock, StockId>, StockRepository, JpaStockRepositoryCustom {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Stock s WHERE s.id = :id")
	Optional<Stock> findByIdWithLock(@Param("id") StockId id);
}

package takeoff.logistics_service.msa.product.stock.infrastructure.persistence;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;
import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;
import takeoff.logistics_service.msa.product.stock.domain.repository.StockRepository;
import takeoff.logistics_service.msa.product.stock.infrastructure.persistence.aspect.LockTimeout;

public interface JpaStockRepository
	extends JpaRepository<Stock, StockId>, StockRepository, JpaStockRepositoryCustom {

	@LockTimeout(timeout = 1000)
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Stock s WHERE s.id = :id AND s.deletedAt is null")
	Optional<Stock> findByIdWithLock(@Param("id") StockId id);

	Optional<Stock> findByIdAndDeletedAtIsNull(StockId id);

	List<Stock> findAllById_ProductIdAndDeletedAtIsNull(UUID productId);

	List<Stock> findAllById_HubIdAndDeletedAtIsNull(UUID hubId);

	List<Stock> findAllById_ProductIdAndDeletedAtIsNullOrderByQuantityDesc(UUID productId);

	boolean existsById(StockId id);
}

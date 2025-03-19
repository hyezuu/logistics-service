package takeoff.logistics_service.msa.product.product.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.product.product.domain.entity.Product;
import takeoff.logistics_service.msa.product.product.domain.repository.ProductRepository;

public interface JpaProductRepository
	extends JpaRepository<Product, UUID>, ProductRepository, JpaProductRepositoryCustom {

	Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);
}

package takeoff.logistics_service.msa.product.product.model.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.product.product.model.entity.Product;

public interface ProductRepository {

	Product save(Product product);

	Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);
}

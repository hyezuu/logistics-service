package takeoff.logistics_service.msa.product.product.domain.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.product.product.domain.entity.Product;
import takeoff.logistics_service.msa.product.product.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteria;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteriaResponse;

public interface ProductRepository {

	Product save(Product product);

	Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);

	PaginatedResult<ProductSearchCriteriaResponse> search(ProductSearchCriteria searchCriteria);

	void delete(Product product);
}

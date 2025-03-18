package takeoff.logistics_service.msa.product.product.model.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.product.product.model.entity.Product;
import takeoff.logistics_service.msa.product.product.model.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.product.model.repository.search.ProductSearchCriteria;
import takeoff.logistics_service.msa.product.product.model.repository.search.ProductSearchCriteriaResponse;

public interface ProductRepository {

	Product save(Product product);

	Optional<Product> findByIdAndDeletedAtIsNull(UUID productId);

	PaginatedResult<ProductSearchCriteriaResponse> search(ProductSearchCriteria searchCriteria);
}

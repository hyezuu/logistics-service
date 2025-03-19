package takeoff.logistics_service.msa.product.product.infrastructure.persistence;

import takeoff.logistics_service.msa.product.product.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteria;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteriaResponse;

public interface JpaProductRepositoryCustom {

	PaginatedResult<ProductSearchCriteriaResponse> search(ProductSearchCriteria criteria);
}

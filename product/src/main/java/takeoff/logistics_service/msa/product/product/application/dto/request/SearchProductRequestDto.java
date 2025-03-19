package takeoff.logistics_service.msa.product.product.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteria;

@Builder
public record SearchProductRequestDto(
	UUID companyId, Boolean isAsc, String sortBy, int page, int size) {

	public ProductSearchCriteria toSearchCriteria() {
		return new ProductSearchCriteria(companyId, isAsc, sortBy, page, size);
	}
}

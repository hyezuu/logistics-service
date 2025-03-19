package takeoff.logistics_service.msa.product.product.domain.repository.search;

import java.util.UUID;

public record ProductSearchCriteria(
	UUID companyId, Boolean isAsc, String sortBy, int page, int size) {
}

package takeoff.logistics_service.msa.product.product.domain.repository.search;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductSearchCriteriaResponse(
	UUID productId, String name, UUID companyId,
	LocalDateTime createdAt, LocalDateTime updatedAt) {

}

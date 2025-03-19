package takeoff.logistics_service.msa.company.domain.repository.search;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logistics_service.msa.company.domain.entity.CompanyType;

public record CompanySearchCriteriaResponse(
	UUID companyId, String companyName, CompanyType companyType,
	UUID hubId, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {
}

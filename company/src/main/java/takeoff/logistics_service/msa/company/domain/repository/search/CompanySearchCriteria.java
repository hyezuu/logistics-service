package takeoff.logistics_service.msa.company.domain.repository.search;

import java.util.UUID;
import takeoff.logistics_service.msa.company.domain.entity.CompanyType;

public record CompanySearchCriteria(
	String companyName, CompanyType companyType, UUID hubId, String address,
	Boolean isAsc, String sortBy, Integer page, Integer size) {
}

package takeoff.logistics_service.msa.company.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.domain.entity.CompanyType;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteria;

@Builder
public record SearchCompanyRequestDto(
	String companyName, String companyType, UUID hubId, String address,
	Boolean isAsc, String sortBy, Integer page, Integer size) {

	public CompanySearchCriteria toSearchCriteria() {
		return new CompanySearchCriteria(companyName, CompanyType.from(companyType), hubId, address, isAsc, sortBy, page, size);
	}
}

package takeoff.logistics_service.msa.company.infrastructure.persistence;

import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteria;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteriaResponse;
import takeoff.logistics_service.msa.company.domain.repository.search.PaginatedResult;

public interface JpaCompanyRepositoryCustom {

	PaginatedResult<CompanySearchCriteriaResponse> search(CompanySearchCriteria criteria);
}

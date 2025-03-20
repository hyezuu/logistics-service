package takeoff.logistics_service.msa.company.domain.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.company.domain.entity.Company;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteria;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteriaResponse;
import takeoff.logistics_service.msa.company.domain.repository.search.PaginatedResult;

public interface CompanyRepository {

	Company save(Company company);

	boolean existsByCompanyName(String companyName);

	Optional<Company> findByIdAndDeletedAtIsNull(UUID companyId);

	PaginatedResult<CompanySearchCriteriaResponse> search(CompanySearchCriteria searchCriteria);
}

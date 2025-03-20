package takeoff.logistics_service.msa.company.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.company.domain.entity.Company;
import takeoff.logistics_service.msa.company.domain.repository.CompanyRepository;

public interface JpaCompanyRepository
	extends JpaRepository<Company, UUID>, CompanyRepository, JpaCompanyRepositoryCustom {

	Optional<Company> findByIdAndDeletedAtIsNull(UUID companyId);

	boolean existsByCompanyName(String companyName);
}

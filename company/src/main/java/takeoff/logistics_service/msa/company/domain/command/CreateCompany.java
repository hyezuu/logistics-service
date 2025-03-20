package takeoff.logistics_service.msa.company.domain.command;

import java.util.UUID;
import takeoff.logistics_service.msa.company.domain.entity.CompanyType;

public record CreateCompany(
	String companyName, CompanyType companyType, UUID hubId, String address) {
}

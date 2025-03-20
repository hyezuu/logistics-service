package takeoff.logistics_service.msa.company.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.domain.command.CreateCompany;
import takeoff.logistics_service.msa.company.domain.entity.CompanyType;

@Builder
public record PostCompanyRequestDto(
	String companyName, String companyType, UUID hubId, String address){

	public CreateCompany toCommand() {
		return new CreateCompany(companyName, CompanyType.from(companyType), hubId, address);
	}
}

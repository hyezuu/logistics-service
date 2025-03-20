package takeoff.logistics_service.msa.company.application.dto.request;

import java.util.UUID;
import takeoff.logistics_service.msa.company.domain.command.ModifyCompany;
import takeoff.logistics_service.msa.company.domain.entity.CompanyType;

public record PutCompanyRequestDto(
	String companyName, String companyType, UUID hubId, String address){

	public ModifyCompany toCommand() {
		return new ModifyCompany(companyName, CompanyType.from(companyType), hubId, address);
	}
}

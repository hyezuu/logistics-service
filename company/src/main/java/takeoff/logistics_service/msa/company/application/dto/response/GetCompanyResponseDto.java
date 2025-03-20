package takeoff.logistics_service.msa.company.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.domain.entity.Company;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteriaResponse;

@Builder
public record GetCompanyResponseDto(
	UUID companyId, String companyName, String companyType,
	UUID hubId, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static GetCompanyResponseDto from(Company company) {
		return GetCompanyResponseDto.builder()
			.companyId(company.getId())
			.companyName(company.getCompanyName())
			.companyType(company.getCompanyType().toString())
			.hubId(company.getHubId())
			.address(company.getAddress())
			.createdAt(company.getCreatedAt())
			.updatedAt(company.getUpdatedAt())
			.build();

	}

	public static GetCompanyResponseDto from(CompanySearchCriteriaResponse response) {
		return GetCompanyResponseDto.builder()
			.companyId(response.companyId())
			.companyName(response.companyName())
			.companyType(response.companyType().toString())
			.hubId(response.hubId())
			.address(response.address())
			.createdAt(response.createdAt())
			.updatedAt(response.updatedAt())
			.build();
	}
}

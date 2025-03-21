package takeoff.logistics_service.msa.company.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.domain.entity.Company;

@Builder
public record PostCompanyResponseDto(
	UUID companyId, String companyName, String companyType,
	UUID hubId, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static PostCompanyResponseDto from(Company company) {
		return PostCompanyResponseDto.builder()
			.companyId(company.getId())
			.companyName(company.getCompanyName())
			.companyType(company.getCompanyType().toString())
			.hubId(company.getHubId())
			.address(company.getAddress())
			.createdAt(company.getCreatedAt())
			.updatedAt(company.getUpdatedAt())
			.build();
	}
}

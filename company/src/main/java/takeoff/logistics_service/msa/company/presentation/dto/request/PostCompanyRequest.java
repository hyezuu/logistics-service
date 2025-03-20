package takeoff.logistics_service.msa.company.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import takeoff.logistics_service.msa.company.application.dto.request.PostCompanyRequestDto;

public record PostCompanyRequest(
	@NotNull String companyName, @NotNull String companyType,
	@NotNull UUID hubId, @NotNull String address) {

	public PostCompanyRequestDto toApplicationDto() {
		return PostCompanyRequestDto.builder()
			.companyName(companyName)
			.companyType(companyType)
			.hubId(hubId)
			.address(address)
			.build();
	}
}

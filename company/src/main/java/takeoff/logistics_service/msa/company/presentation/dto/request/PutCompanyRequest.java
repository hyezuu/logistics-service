package takeoff.logistics_service.msa.company.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import takeoff.logistics_service.msa.company.application.dto.request.PutCompanyRequestDto;

public record PutCompanyRequest(
	@NotNull String companyName, @NotNull String companyType,
	@NotNull UUID hubId, @NotNull String address) {

	public PutCompanyRequestDto toApplicationDto() {
		return new PutCompanyRequestDto(companyName, companyType, hubId, address);
	}
}

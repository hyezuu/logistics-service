package takeoff.logistics_service.msa.product.product.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostProductRequestDto;

public record PostProductRequest(
	@NotNull String name, @NotNull UUID companyId,
	@NotNull UUID hubId, @PositiveOrZero Integer quantity) {

	public PostProductRequest {
		quantity = quantity != null ? quantity : 0;
	}

	public PostProductRequestDto toApplicationDto() {
		return PostProductRequestDto.builder()
			.name(name)
			.companyId(companyId)
			.hubId(hubId)
			.quantity(quantity).build();
	}
}

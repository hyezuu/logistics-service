package takeoff.logistics_service.msa.product.product.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.product.product.application.dto.request.PatchProductRequestDto;

public record PatchProductRequest(@NotNull String name) {

	public PatchProductRequestDto toApplicationDto(){
		return new PatchProductRequestDto(name);
	}
}

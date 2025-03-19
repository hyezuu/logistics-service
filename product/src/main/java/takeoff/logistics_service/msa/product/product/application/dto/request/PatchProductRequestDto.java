package takeoff.logistics_service.msa.product.product.application.dto.request;

import takeoff.logistics_service.msa.product.product.domain.command.ModifyProduct;

public record PatchProductRequestDto(String name) {

	public ModifyProduct toCommand() {
		return new ModifyProduct(name);
	}
}

package takeoff.logistics_service.msa.product.product.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.domain.command.CreateProduct;

@Builder
public record PostProductRequestDto(String name, UUID companyId, UUID hubId, Integer quantity){

	public CreateProduct toCommand() {
		return new CreateProduct(name, companyId);
	}
}

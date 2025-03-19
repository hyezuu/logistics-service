package takeoff.logistics_service.msa.product.product.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.domain.entity.Product;

@Builder
public record PatchProductResponseDto(
	UUID productId, String name, UUID companyId, LocalDateTime updatedAt) {

	public static PatchProductResponseDto from (Product product){
		return PatchProductResponseDto.builder()
			.productId(product.getId())
			.name(product.getName())
			.companyId(product.getCompanyId())
			.updatedAt(product.getUpdatedAt())
			.build();
	}

}

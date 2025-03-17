package takeoff.logistics_service.msa.product.product.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.model.entity.Product;

@Builder
public record GetProductResponseDto(UUID productId, String name, UUID companyId,
									LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static GetProductResponseDto from(Product product) {
		return  GetProductResponseDto.builder()
			.productId(product.getId())
			.name(product.getName())
			.companyId(product.getCompanyId())
			.createdAt(product.getCreatedAt())
			.updatedAt(product.getUpdatedAt())
			.build();
	}
}

package takeoff.logistics_service.msa.product.product.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.domain.entity.Product;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteriaResponse;

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

	public static GetProductResponseDto from(ProductSearchCriteriaResponse response) {
		return GetProductResponseDto.builder()
			.productId(response.productId())
			.name(response.name())
			.companyId(response.companyId())
			.createdAt(response.createdAt())
			.updatedAt(response.updatedAt())
			.build();
	}
}

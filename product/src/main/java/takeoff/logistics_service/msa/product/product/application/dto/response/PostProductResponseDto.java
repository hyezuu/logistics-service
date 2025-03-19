package takeoff.logistics_service.msa.product.product.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.domain.entity.Product;

@Builder
public record PostProductResponseDto(
	UUID productId, String name, UUID companyId, UUID hubId,
	Integer quantity, LocalDateTime createdAt) {

	public static PostProductResponseDto from(Product product, PostStockResponseDto responseDto) {
		return PostProductResponseDto.builder()
			.productId(product.getId())
			.name(product.getName())
			.companyId(product.getCompanyId())
			.hubId(responseDto.hubId())
			.quantity(responseDto.quantity())
			.createdAt(responseDto.createdAt())
			.build();
	}
}

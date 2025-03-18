package takeoff.logistics_service.msa.product.product.application.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record PostStockRequestDto(UUID productId, UUID hubId, Integer quantity) {

	public static PostStockRequestDto from(UUID productId, PostProductRequestDto requestDto) {
		return PostStockRequestDto.builder()
			.productId(productId)
			.hubId(requestDto.hubId())
			.quantity(requestDto.quantity())
			.build();
	}
}

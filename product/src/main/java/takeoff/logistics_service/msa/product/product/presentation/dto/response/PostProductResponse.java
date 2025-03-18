package takeoff.logistics_service.msa.product.product.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostProductResponseDto;

@Builder
public record PostProductResponse(UUID productId, String name, UUID companyId, UUID hubId,
								  Integer quantity, LocalDateTime createdAt) {

	public static PostProductResponse from (PostProductResponseDto responseDto) {
		return PostProductResponse.builder()
			.productId(responseDto.productId())
			.name(responseDto.name())
			.companyId(responseDto.companyId())
			.hubId(responseDto.hubId())
			.quantity(responseDto.quantity())
			.createdAt(responseDto.createdAt())
			.build();
	}
}

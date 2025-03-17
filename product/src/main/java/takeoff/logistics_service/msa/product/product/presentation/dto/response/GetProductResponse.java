package takeoff.logistics_service.msa.product.product.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetProductResponseDto;

@Builder
public record GetProductResponse(UUID productId, String name, UUID companyId,
									LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static GetProductResponse from (GetProductResponseDto responseDto){
		return GetProductResponse.builder()
			.productId(responseDto.productId())
			.name(responseDto.name())
			.companyId(responseDto.companyId())
			.createdAt(responseDto.createdAt())
			.updatedAt(responseDto.updatedAt())
			.build();
	}
}

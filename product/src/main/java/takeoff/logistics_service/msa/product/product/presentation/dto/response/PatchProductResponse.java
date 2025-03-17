package takeoff.logistics_service.msa.product.product.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.application.dto.response.PatchProductResponseDto;

@Builder
public record PatchProductResponse(
	UUID productId, String name, UUID companyId, LocalDateTime updatedAt) {

	public static PatchProductResponse from (PatchProductResponseDto responseDto) {
		return PatchProductResponse.builder()
			.productId(responseDto.productId())
			.name(responseDto.name())
			.companyId(responseDto.companyId())
			.updatedAt(responseDto.updatedAt())
			.build();
	}
}

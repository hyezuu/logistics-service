package takeoff.logistics_service.msa.product.product.presentation.dto;

import java.util.List;
import takeoff.logistics_service.msa.product.product.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetProductResponseDto;
import takeoff.logistics_service.msa.product.product.presentation.dto.response.GetProductResponse;

public record PaginatedResultApi<T>(List<T> content,
									int page,
									int size,
									Long totalElements,
									int totalPages) {

	public static PaginatedResultApi<GetProductResponse> from(
		PaginatedResultDto<GetProductResponseDto> result) {

		return new PaginatedResultApi<>(
			result.content().stream()
				.map(GetProductResponse::from)
				.toList(),
			result.page(),
			result.size(),
			result.totalElements(),
			result.totalPages()
		);
	}

}
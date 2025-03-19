package takeoff.logistics_service.msa.product.product.application.dto;

import java.util.List;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetProductResponseDto;
import takeoff.logistics_service.msa.product.product.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteriaResponse;

public record PaginatedResultDto<T>(List<T> content,
								 int page,
								 int size,
								 Long totalElements,
								 int totalPages) {

	public static PaginatedResultDto<GetProductResponseDto> from(
		PaginatedResult<ProductSearchCriteriaResponse> result) {

		return new PaginatedResultDto<>(
			result.content().stream()
				.map(GetProductResponseDto::from)
				.toList(),
			result.page(),
			result.size(),
			result.totalElements(),
			result.totalPages()
		);
	}
}

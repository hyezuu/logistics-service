package takeoff.logistics_service.msa.product.stock.application.dto;

import java.util.List;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteriaResponse;

public record PaginatedResultDto<T>(List<T> content,
									int page,
									int size,
									Long totalElements,
									int totalPages) {

	public static PaginatedResultDto<GetStockResponseDto> from(
		PaginatedResult<StockSearchCriteriaResponse> result) {

		return new PaginatedResultDto<>(
			result.content().stream()
				.map(GetStockResponseDto::from)
				.toList(),
			result.page(),
			result.size(),
			result.totalElements(),
			result.totalPages()
		);
	}
}

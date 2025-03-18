package takeoff.logistics_service.msa.product.stock.presentation.dto;

import java.util.List;
import takeoff.logistics_service.msa.product.stock.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponse;

public record PaginatedResultApi<T>(List<T> content,
									int page,
									int size,
									Long totalElements,
									int totalPages) {

	public static PaginatedResultApi<GetStockResponse> from(
		PaginatedResultDto<GetStockResponseDto> result) {

		return new PaginatedResultApi<>(
			result.content().stream()
				.map(GetStockResponse::from)
				.toList(),
			result.page(),
			result.size(),
			result.totalElements(),
			result.totalPages()
		);
	}
}
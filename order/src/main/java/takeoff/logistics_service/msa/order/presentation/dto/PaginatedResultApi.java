package takeoff.logistics_service.msa.order.presentation.dto;

import java.util.List;
import takeoff.logistics_service.msa.order.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.order.application.dto.response.SearchOrderResponseDto;
import takeoff.logistics_service.msa.order.presentation.dto.response.SearchOrderResponse;

public record PaginatedResultApi<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {

  public static PaginatedResultApi<SearchOrderResponse> from(
      PaginatedResultDto<SearchOrderResponseDto> result) {
    return new PaginatedResultApi<>(
        result.content().stream()
            .map(SearchOrderResponse::from)
            .toList(),
        result.page(),
        result.size(),
        result.totalElements(),
        result.totalPages());
  }
}

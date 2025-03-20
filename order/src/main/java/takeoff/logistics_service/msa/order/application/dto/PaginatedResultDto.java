package takeoff.logistics_service.msa.order.application.dto;

import java.util.List;
import takeoff.logistics_service.msa.order.application.dto.response.SearchOrderResponseDto;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteriaResponse;
import takeoff.logistics_service.msa.order.domain.repository.search.PaginatedResult;

public record PaginatedResultDto<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {

  public static PaginatedResultDto<SearchOrderResponseDto> from(
      PaginatedResult<OrderSearchCriteriaResponse> result) {
    return new PaginatedResultDto<>(
        result.content().stream()
            .map(SearchOrderResponseDto::from)
            .toList(),
        result.page(),
        result.size(),
        result.totalElements(),
        result.totalPages());
  }
}

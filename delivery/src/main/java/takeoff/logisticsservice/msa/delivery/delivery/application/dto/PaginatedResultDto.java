package takeoff.logisticsservice.msa.delivery.delivery.application.dto;

import java.util.List;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.response.SearchDeliveryResponseDto;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteriaResponse;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.PaginatedResult;

public record PaginatedResultDto<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {

  public static PaginatedResultDto<SearchDeliveryResponseDto> from(
      PaginatedResult<DeliverySearchCriteriaResponse> result) {
    return new PaginatedResultDto<>(
        result.content().stream()
            .map(SearchDeliveryResponseDto::from)
            .toList(),
        result.page(),
        result.size(),
        result.totalElements(),
        result.totalPages());
  }
}

package takeoff.logisticsservice.msa.delivery.delivery.presentation.dto;

import java.util.List;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.PaginatedResultDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.response.SearchDeliveryResponseDto;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.response.SearchDeliveryResponse;

public record PaginatedResultApi<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {

  public static PaginatedResultApi<SearchDeliveryResponse> from(
      PaginatedResultDto<SearchDeliveryResponseDto> result) {
    return new PaginatedResultApi<>(
        result.content().stream()
            .map(SearchDeliveryResponse::from)
            .toList(),
        result.page(),
        result.size(),
        result.totalElements(),
        result.totalPages());
  }
}

package takeoff.logistics_service.msa.order.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteriaResponse;

public record SearchOrderResponseDto(
    UUID orderId,
    UUID supplierId,
    List<SearchOrderItemResponseDto> orderItems,
    Long customerId,
    String address,
    String requestNotes,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {

  public static SearchOrderResponseDto from(OrderSearchCriteriaResponse response) {
    return new SearchOrderResponseDto(
        response.orderId(),
        response.supplierId(),
        response.orderItems().stream()
            .map(SearchOrderItemResponseDto::from)
            .toList(),
        response.customerId(),
        response.address(),
        response.requestNotes(),
        response.createdAt(),
        response.createdBy(),
        response.updatedAt(),
        response.updatedBy()
    );
  }
}

package takeoff.logistics_service.msa.order.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.order.application.dto.response.SearchOrderResponseDto;

public record SearchOrderResponse(
    UUID orderId,
    UUID supplierId,
    List<SearchOrderItemResponse> orderItems,
    Long customerId,
    String address,
    String requestNotes,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {

  public static SearchOrderResponse from(SearchOrderResponseDto dto) {
    return new SearchOrderResponse(
        dto.orderId(),
        dto.supplierId(),
        dto.orderItems().stream()
            .map(SearchOrderItemResponse::from)
            .toList(),
        dto.customerId(),
        dto.address(),
        dto.requestNotes(),
        dto.createdAt(),
        dto.createdBy(),
        dto.updatedAt(),
        dto.updatedBy()
    );
  }
}

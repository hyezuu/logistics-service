package takeoff.logistics_service.msa.order.presentation.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.order.application.dto.response.SearchOrderItemResponseDto;

public record SearchOrderItemResponse(
    UUID productId,
    Integer quantity
) {

  public static SearchOrderItemResponse from(SearchOrderItemResponseDto dto) {
    return new SearchOrderItemResponse(
        dto.productId(),
        dto.quantity()
    );
  }
}

package takeoff.logistics_service.msa.order.application.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.entity.OrderItem;

public record SearchOrderItemResponseDto(
    UUID productId,
    int quantity
) {

  public static SearchOrderItemResponseDto from(OrderItem orderItem) {
    return new SearchOrderItemResponseDto(
        orderItem.getProductId(),
        orderItem.getQuantity()
    );
  }
}

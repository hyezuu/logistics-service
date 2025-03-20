package takeoff.logistics_service.msa.order.presentation.dto.response;

import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderItem;


public record PatchOrderResponse(List<PatchOrderItemResponseDto> orderItems) {

  public static PatchOrderResponse from(Order order) {
    return new PatchOrderResponse(
        order.getOrderItems().stream()
            .map(PatchOrderItemResponseDto::from)
            .toList()
    );
  }

  private record PatchOrderItemResponseDto(
      UUID productId,
      int quantity
  ) {

    public static PatchOrderItemResponseDto from(OrderItem orderItem) {
      return new PatchOrderItemResponseDto(
          orderItem.getProductId(),
          orderItem.getQuantity()
      );
    }
  }
}

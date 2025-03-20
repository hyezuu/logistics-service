package takeoff.logistics_service.msa.order.presentation.dto.response;

import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderItem;

public record PatchOrderResponseDto(List<PatchOrderItemResponseDto> orderItems) {

  public static PatchOrderResponseDto from(Order order) {
    return new PatchOrderResponseDto(
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

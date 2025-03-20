package takeoff.logistics_service.msa.order.presentation.dto.response;


import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.entity.Order;

public record PostOrderResponse(
    UUID orderId,
    UUID supplierId,
    List<OrderItemSaveResponseDto> orderItems,
    Long customerId,
    String address,
    String requestNotes
) {

  public static PostOrderResponse from(Order order) {
    return new PostOrderResponse(
        order.getId().getOrderId(),
        order.getSupplierId(),
        order.getOrderItems().stream()
            .map(orderItem -> new OrderItemSaveResponseDto(orderItem.getProductId(),
                orderItem.getQuantity()))
            .toList(),
        order.getCustomerId(),
        order.getAddress(),
        order.getRequestNotes()
    );
  }

  private record OrderItemSaveResponseDto(
      UUID productId,
      int quantity
  ) {

  }
}

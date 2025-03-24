package takeoff.logistics_service.msa.order.domain.repository.search;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderItem;

public record OrderSearchCriteriaResponse(
    UUID orderId,
    UUID supplierId,
    List<OrderItem> orderItems,
    Long customerId,
    String address,
    String requestNotes,
    LocalDateTime createdAt,
    Long createdBy,
    LocalDateTime updatedAt,
    Long updatedBy
) {
  public static OrderSearchCriteriaResponse from(Order order) {
    return new OrderSearchCriteriaResponse(
        order.getId().getOrderId(),
        order.getSupplierId(),
        order.getOrderItems(),
        order.getCustomerId(),
        order.getAddress(),
        order.getRequestNotes(),
        order.getCreatedAt(),
        order.getCreatedBy(),
        order.getUpdatedAt(),
        order.getUpdatedBy()
    );
  }
}

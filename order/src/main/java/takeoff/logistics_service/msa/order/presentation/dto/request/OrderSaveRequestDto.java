package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.List;
import java.util.UUID;

public record OrderSaveRequestDto(
    UUID supplierId,
    Long customerId,
    List<OrderItemSaveRequestDto> orderItems,
    String deliveryAddress,
    String requestNotes
) {

}

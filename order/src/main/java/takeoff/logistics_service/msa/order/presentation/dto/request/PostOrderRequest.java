package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.List;
import java.util.UUID;

public record PostOrderRequestDto(
    UUID supplierId,
    Long customerId,
    List<PostOrderItemRequestDto> orderItems,
    String deliveryAddress,
    String requestNotes
) {

}

package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.List;
import java.util.UUID;

public record PostOrderRequest(
    UUID supplierId,
    UUID companyId,
    Long customerId,
    List<PostOrderItemRequest> orderItems,
    String deliveryAddress,
    String requestNotes
) {

}

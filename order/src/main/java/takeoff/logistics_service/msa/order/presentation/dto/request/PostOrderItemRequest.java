package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.UUID;


public record PostOrderItemRequest(
    UUID productId,
    int quantity
) {

}

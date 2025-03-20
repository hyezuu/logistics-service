package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.UUID;


public record PatchOrderItemRequest(
    UUID productId,
    int quantity
) {

}

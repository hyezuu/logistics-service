package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.UUID;

public record PostOrderItemRequestDto(
    UUID productId,
    int quantity
) {

}

package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.List;

public record PatchOrderRequest(List<PatchOrderItemRequest> orderItems) {

}

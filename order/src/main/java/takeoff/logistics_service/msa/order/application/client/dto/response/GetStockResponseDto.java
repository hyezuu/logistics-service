package takeoff.logistics_service.msa.order.application.client.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetStockResponseDto(
    UUID productId,
    UUID hubId,
    Integer quantity,
    LocalDateTime updatedAt) {

}

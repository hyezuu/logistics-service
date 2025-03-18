package takeoff.logistics_service.msa.product.product.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record PostStockResponseDto (
	UUID productId, UUID hubId, Integer quantity, LocalDateTime createdAt) {
}

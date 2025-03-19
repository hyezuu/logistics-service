package takeoff.logistics_service.msa.product.stock.domain.command;

import java.util.UUID;

public record CreateStockId(UUID productId, UUID hubId) {
}

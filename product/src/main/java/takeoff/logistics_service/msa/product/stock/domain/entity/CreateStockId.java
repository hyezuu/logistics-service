package takeoff.logistics_service.msa.product.stock.domain.entity;

import java.util.UUID;

public record CreateStockId(UUID productId, UUID hubId) {
}

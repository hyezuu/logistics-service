package takeoff.logistics_service.msa.product.stock.domain.repository.search;

import java.util.UUID;

public record StockIdSearchCriteriaResponse(UUID productId, UUID hubId) {

}

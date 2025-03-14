package takeoff.logistics_service.msa.product.stock.presentation.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.stock.model.entity.StockId;

@Builder
public record StockIdDto(@NotNull UUID productId, @NotNull UUID hubId) {

	public StockId toVo() {
		return StockId.create(productId, hubId);
	}

	public static StockIdDto from(StockId stockId) {
		return StockIdDto.builder()
			.productId(stockId.getProductId())
			.hubId(stockId.getHubId())
			.build();
	}
}

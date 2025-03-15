package takeoff.logistics_service.msa.product.stock.model.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockId implements Serializable {

	private UUID productId;

	private UUID hubId;

	public static StockId create(UUID productId, UUID hubId) {
		return new StockId(productId, hubId);
	}

	private StockId(UUID productId, UUID hubId) {
		if (productId == null || hubId == null) {
			//도메인 예외로 ~
			throw new IllegalArgumentException("Product ID 와 Hub ID 는 null 일 수 없습니다.");
		}
		this.productId = productId;
		this.hubId = hubId;
	}
}

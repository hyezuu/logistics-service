package takeoff.logistics_service.msa.product.stock.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.common.domain.BaseEntity;

@Getter
@Entity
@Table(name = "p_stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseEntity {

	@EmbeddedId
	private StockId id;

	private Integer quantity;

	@Builder
	private Stock(StockId id, Integer quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public void increaseStock(Integer quantity) {
		this.quantity += quantity;
	}

	public void decreaseStock(Integer quantity) {
		if (quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("수량은 양수여야 합니다.");
		}
		if (this.quantity < quantity) {
			throw new IllegalStateException("재고가 부족합니다.");
		}
		this.quantity -= quantity;
	}
}

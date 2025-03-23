package takeoff.logistics_service.msa.product.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import takeoff.logistics_service.msa.common.domain.BaseEntity;
import takeoff.logistics_service.msa.product.product.domain.command.CreateProduct;
import takeoff.logistics_service.msa.product.product.domain.command.ModifyProduct;

@Getter
@Entity
@Table(name = "p_product")
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "product_id")
	private UUID id;

	private String name;

	private UUID companyId;

	public static Product create(CreateProduct command){
		return new Product(command.name(), command.companyId());
	}

	public Product modify(ModifyProduct command){
		this.name = command.name();
		return this;
	}

	private Product(String name, UUID companyId) {
		this.name = name;
		this.companyId = companyId;
	}

	protected Product() {
	}
}

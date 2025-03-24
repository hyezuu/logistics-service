package takeoff.logistics_service.msa.company.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import takeoff.logistics_service.msa.common.domain.BaseEntity;
import takeoff.logistics_service.msa.company.domain.command.CreateCompany;
import takeoff.logistics_service.msa.company.domain.command.ModifyCompany;

@Getter
@Entity
@Table(name = "p_company")
public class Company extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "company_id")
	private UUID id;

	@Column(nullable = false, unique = true)
	private String companyName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CompanyType companyType;

	@Column(nullable = false)
	private UUID hubId;

	@Column(nullable = false)
	private String address;

	public static Company create(CreateCompany command){
		return new Company(
			command.companyName(), command.companyType(), command.hubId(), command.address());
	}

	public Company modify(ModifyCompany command){
		this.companyName = command.companyName();
		this.companyType = command.companyType();
		this.hubId = command.hubId();
		this.address = command.address();

		return this;
	}

	private Company(String companyName, CompanyType companyType, UUID hubId, String address) {
		this.companyName = companyName;
		this.companyType = companyType;
		this.hubId = hubId;
		this.address = address;
	}

	protected Company() {}
}

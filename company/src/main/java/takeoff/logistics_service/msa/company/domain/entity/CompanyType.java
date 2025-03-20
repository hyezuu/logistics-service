package takeoff.logistics_service.msa.company.domain.entity;

import lombok.Getter;

@Getter
public enum CompanyType {
	SUPPLIER("생산업체"),
	CUSTOMER("수령업체");

	private final String description;

	CompanyType(String description) {
		this.description = description;
	}

	public static CompanyType from(String name) {
		try {
			return name == null ? null : valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid company type: " + name);
		}
	}
}
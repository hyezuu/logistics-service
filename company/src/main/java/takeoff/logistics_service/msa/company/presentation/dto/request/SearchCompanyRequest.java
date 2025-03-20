package takeoff.logistics_service.msa.company.presentation.dto.request;

import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.application.dto.request.SearchCompanyRequestDto;

@Builder
public record SearchCompanyRequest(
	String companyName, String companyType, UUID hubId, String address,
	Boolean isAsc, String sortBy, Integer page, Integer size) {

	public SearchCompanyRequest {
		isAsc = isAsc != null ? isAsc : false;
		sortBy = sortBy != null ? sortBy : "createdAt";
		size = (size != null && Set.of(10, 30, 50).contains(size)) ? size : 10;
		page = page != null ? page : 0;
	}

	public SearchCompanyRequestDto toApplicationDto() {
		return SearchCompanyRequestDto.builder()
			.companyName(companyName)
			.companyType(companyType)
			.hubId(hubId)
			.address(address)
			.isAsc(isAsc)
			.sortBy(sortBy)
			.page(page)
			.size(size)
			.build();
	}
}

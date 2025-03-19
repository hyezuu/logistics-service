package takeoff.logistics_service.msa.product.product.presentation.dto.request;

import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.product.product.application.dto.request.SearchProductRequestDto;

@Builder
public record SearchProductRequest(
	UUID companyId, Boolean isAsc, String sortBy, Integer page, Integer size) {

	public SearchProductRequest {
		isAsc = isAsc != null ? isAsc : false;
		sortBy = sortBy != null ? sortBy : "createdAt";
		size = (size != null && Set.of(10, 30, 50).contains(size)) ? size : 10;
		page = page != null ? page : 0;
	}

	public SearchProductRequestDto toApplicationDto() {
		return SearchProductRequestDto.builder()
			.companyId(companyId)
			.isAsc(isAsc)
			.sortBy(sortBy)
			.page(page)
			.size(size)
			.build();
	}
}

package takeoff.logistics_service.msa.product.stock.presentation.dto.request;

import java.util.Set;
import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.application.dto.request.SearchStockRequestDto;

public record SearchStockRequest(
	UUID productId, UUID hubId, Boolean isAsc, String sortBy, Integer page, Integer size) {

	public SearchStockRequest {
		isAsc = isAsc != null ? isAsc : false;
		sortBy = sortBy != null ? sortBy : "createdAt";
		size = (size != null && Set.of(10, 30, 50).contains(size)) ? size : 10;
		page = page != null ? page : 0;
	}

	public SearchStockRequestDto toApplicationDto() {
		return SearchStockRequestDto.builder()
			.productId(productId)
			.hubId(hubId)
			.isAsc(isAsc)
			.sortBy(sortBy)
			.page(page)
			.size(size)
			.build();
	}
}

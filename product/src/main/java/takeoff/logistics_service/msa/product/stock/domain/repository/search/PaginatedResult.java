package takeoff.logistics_service.msa.product.stock.domain.repository.search;

import java.util.List;

public record PaginatedResult<T>(
	List<T> content, int page, int size, Long totalElements, int totalPages) {
}


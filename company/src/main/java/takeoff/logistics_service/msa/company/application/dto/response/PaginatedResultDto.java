package takeoff.logistics_service.msa.company.application.dto.response;

import java.util.List;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteriaResponse;
import takeoff.logistics_service.msa.company.domain.repository.search.PaginatedResult;


public record PaginatedResultDto<T>(List<T> content,
								 int page,
								 int size,
								 Long totalElements,
								 int totalPages) {

	public static PaginatedResultDto<GetCompanyResponseDto> from(
		PaginatedResult<CompanySearchCriteriaResponse> result) {

		return new PaginatedResultDto<>(
			result.content().stream()
				.map(GetCompanyResponseDto::from)
				.toList(),
			result.page(),
			result.size(),
			result.totalElements(),
			result.totalPages()
		);
	}
}

package takeoff.logistics_service.msa.company.presentation.dto.response;

import java.util.List;
import takeoff.logistics_service.msa.company.application.dto.response.GetCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PaginatedResultDto;


public record PaginatedResultApi<T>(List<T> content,
									int page,
									int size,
									Long totalElements,
									int totalPages) {

	public static PaginatedResultApi<GetCompanyResponse> from(
		PaginatedResultDto<GetCompanyResponseDto> result) {

		return new PaginatedResultApi<>(
			result.content().stream()
				.map(GetCompanyResponse::from)
				.toList(),
			result.page(),
			result.size(),
			result.totalElements(),
			result.totalPages()
		);
	}
}
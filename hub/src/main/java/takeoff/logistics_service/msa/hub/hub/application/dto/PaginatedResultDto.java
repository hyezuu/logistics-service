package takeoff.logistics_service.msa.hub.hub.application.dto;

import java.util.List;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.SearchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteriaResponse;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.PaginatedResult;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
public record PaginatedResultDto<T>(List<T> content,
                                    int page,
                                    int size,
                                    Long totalElements,
                                    int totalPages) {
    public static PaginatedResultDto<SearchHubResponseDto> from(
        PaginatedResult<HubSearchCriteriaResponse> searchResult) {

        return new PaginatedResultDto<>(
            searchResult.content().stream()
                .map(SearchHubResponseDto::from)
                .toList(),
            searchResult.page(),
            searchResult.size(),
            searchResult.totalElements(),
            searchResult.totalPages()
        );
    }

}

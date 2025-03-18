package takeoff.logistics_service.msa.hub.hub.presentation.dto;

import java.util.List;
import takeoff.logistics_service.msa.hub.hub.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.SearchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.SearchHubResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
public record PaginatedResultApi<T>(List<T> content,
                                    int page,
                                    int size,
                                    Long totalElements,
                                    int totalPages) {

    public static PaginatedResultApi<SearchHubResponse> from(
        PaginatedResultDto<SearchHubResponseDto> searchResult) {

        return new PaginatedResultApi<>(
            searchResult.content().stream()
                .map(SearchHubResponse::from)
                .toList(),
            searchResult.page(),
            searchResult.size(),
            searchResult.totalElements(),
            searchResult.totalPages()
        );
    }

}

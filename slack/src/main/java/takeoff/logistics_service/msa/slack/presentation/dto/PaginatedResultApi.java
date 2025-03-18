package takeoff.logistics_service.msa.slack.presentation.dto;

import java.util.List;
import takeoff.logistics_service.msa.slack.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.slack.application.dto.response.SearchSlackResponseDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchSlackResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
public record PaginatedResultApi<T>(List<T> content,
                                    int page,
                                    int size,
                                    Long totalElements,
                                    int totalPages) {

    public static PaginatedResultApi<SearchSlackResponse> from(
        PaginatedResultDto<SearchSlackResponseDto> searchResult) {

        return new PaginatedResultApi<>(
            searchResult.content().stream()
                .map(SearchSlackResponse::from)
                .toList(),
            searchResult.page(),
            searchResult.size(),
            searchResult.totalElements(),
            searchResult.totalPages()
        );
    }

}

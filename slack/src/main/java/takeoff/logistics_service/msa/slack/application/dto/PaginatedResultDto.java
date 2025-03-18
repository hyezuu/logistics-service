package takeoff.logistics_service.msa.slack.application.dto;

import java.util.List;
import takeoff.logistics_service.msa.slack.application.dto.response.SearchSlackResponseDto;
import takeoff.logistics_service.msa.slack.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.slack.domain.repository.search.SlackSearchCriteriaResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
public record PaginatedResultDto<T>(List<T> content,
                                    int page,
                                    int size,
                                    Long totalElements,
                                    int totalPages) {
    public static PaginatedResultDto<SearchSlackResponseDto> from(
        PaginatedResult<SlackSearchCriteriaResponse> searchResult) {

        return new PaginatedResultDto<>(
            searchResult.content().stream()
                .map(SearchSlackResponseDto::from)
                .toList(),
            searchResult.page(),
            searchResult.size(),
            searchResult.totalElements(),
            searchResult.totalPages()
        );
    }
}

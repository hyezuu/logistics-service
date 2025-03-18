package takeoff.logistics_service.msa.slack.domain.repository.search;

import java.util.List;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
public record PaginatedResult<T>(List<T> content,
                                 int page,
                                 int size,
                                 Long totalElements,
                                 int totalPages) {


}

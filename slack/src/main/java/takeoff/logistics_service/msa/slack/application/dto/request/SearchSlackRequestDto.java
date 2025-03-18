package takeoff.logistics_service.msa.slack.application.dto.request;


import java.util.Set;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.domain.repository.search.SlackSearchCriteria;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchSlackRequestDto(String message,
                                    Boolean isAsc,
                                    String sortBy,
                                    int page,
                                    int size
                                 ) {

    public static SlackSearchCriteria toSearchCriteria(SearchSlackRequestDto requestDto) {
        return new SlackSearchCriteria(
            requestDto.message,
            requestDto.isAsc,
            requestDto.sortBy,
            requestDto.page,
            validSize(requestDto.size)
        );
    }

    private static int validSize(int size) {
        return Set.of(10, 30, 50).contains(size) ? size : 10;
    }


}

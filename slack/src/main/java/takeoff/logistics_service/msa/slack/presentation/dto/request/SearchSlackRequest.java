package takeoff.logistics_service.msa.slack.presentation.dto.request;


import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.request.SearchSlackRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchSlackRequest(String message,
                                 Boolean isAsc,
                                 String sortBy,
                                 int page,
                                 int size
                                 ) {
    public SearchSlackRequestDto toApplicationDto() {
        return SearchSlackRequestDto.builder()
            .message(message())
            .isAsc(isAsc())
            .sortBy(sortBy())
            .page(page())
            .size(size())
            .build();
    }
}

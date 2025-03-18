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
    public static SearchSlackRequestDto from(SearchSlackRequest requestDto) {
        return SearchSlackRequestDto.builder()
            .message(requestDto.message())
            .isAsc(requestDto.isAsc())
            .sortBy(requestDto.sortBy())
            .page(requestDto.page())
            .size(requestDto.size())
            .build();
    }
}

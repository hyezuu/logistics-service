package takeoff.logistics_service.msa.slack.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.application.dto.request.PostUserSlackRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 20.
 */
public record PostUserSlackRequest(@NotNull PostContentsRequest postContentsRequest) {

    public PostUserSlackRequestDto toApplicationDto() {
        return PostUserSlackRequestDto.builder()
            .postContentsRequestDto(postContentsRequest.toApplicationDto())
            .build();
    }

}

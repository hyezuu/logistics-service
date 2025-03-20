package takeoff.logistics_service.msa.slack.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.application.dto.request.PostContentsRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record PostContentsRequest(@NotNull String message) {

    public PostContentsRequestDto toApplicationDto() {
        return PostContentsRequestDto.builder()
            .message(message)
            .build();
    }
}

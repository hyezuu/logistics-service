package takeoff.logistics_service.msa.slack.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.domain.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record PostContentsRequest(@NotNull String message) {

    public Contents toVo() {
        return Contents.create(message);
    }

}

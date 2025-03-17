package takeoff.logistics_service.msa.slack.application.dto.request;

import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.model.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record PostContentsRequestDto(String message) {

    public Contents toVo() {
        return Contents.create(message);
    }

}

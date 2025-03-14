package takeoff.logistics_service.msa.slack.presentation.dto.request;

import takeoff.logistics_service.msa.slack.model.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record GetContentsRequestDto(String message) {

    public Contents toVo() {
        return Contents.create(message);
    }

}

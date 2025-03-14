package takeoff.logistics_service.msa.slack.presentation.dto.request;

import takeoff.logistics_service.msa.slack.model.entity.Contents;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record ContentsRequestDto(String message) {

    public Contents toVo() {
        return Contents.createContents(message);
    }

}

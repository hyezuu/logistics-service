package takeoff.logistics_service.msa.slack.application.dto.request;

import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PostContentsRequestDto(String message) {

}

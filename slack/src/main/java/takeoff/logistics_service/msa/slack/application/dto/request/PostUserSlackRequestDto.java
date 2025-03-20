package takeoff.logistics_service.msa.slack.application.dto.request;

import lombok.Builder;
import takeoff.logistics_service.msa.slack.domain.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 20.
 */
@Builder
public record PostUserSlackRequestDto(PostContentsRequestDto postContentsRequestDto) {

    public Slack toEntity(Long userId) {
        return Slack.createSlack(userId, postContentsRequestDto().message());
    }

}

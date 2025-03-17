package takeoff.logistics_service.msa.slack.presentation.dto.request;


import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record SearchSlackRequest(Long userId,
                                 SearchContentsRequest searchContentsRequest) {

    public Slack toEntity() {
        return Slack.builder()
            .userId(userId)
            .contents(searchContentsRequest.toVo())
            .build();
    }

}

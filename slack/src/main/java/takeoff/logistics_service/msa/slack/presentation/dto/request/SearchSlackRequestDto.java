package takeoff.logistics_service.msa.slack.presentation.dto.request;


import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record SearchSlackRequestDto(Long userId,
                                    SearchContentsRequestDto searchContentsRequestDto) {

    public Slack toEntity() {
        return Slack.builder()
            .userId(userId)
            .contents(searchContentsRequestDto.toVo())
            .build();
    }

}

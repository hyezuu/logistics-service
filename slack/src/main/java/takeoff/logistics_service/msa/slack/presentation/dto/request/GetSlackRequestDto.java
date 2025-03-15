package takeoff.logistics_service.msa.slack.presentation.dto.request;


import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record GetSlackRequestDto(Long userId,
                                 GetContentsRequestDto getContentsRequestDto) {

    public Slack toEntity() {
        return Slack.builder()
            .userId(userId)
            .contents(getContentsRequestDto.toVo())
            .build();
    }

}

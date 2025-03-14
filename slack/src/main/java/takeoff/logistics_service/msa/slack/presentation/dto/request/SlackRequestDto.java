package takeoff.logistics_service.msa.slack.presentation.dto.request;


import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Contents;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record SlackRequestDto(Long userId,
                              ContentsRequestDto contentsRequestDto) {

    public Slack toEntity() {
        return Slack.builder()
            .userId(userId)
            .contents(contentsRequestDto.toVo())
            .build();
    }

}

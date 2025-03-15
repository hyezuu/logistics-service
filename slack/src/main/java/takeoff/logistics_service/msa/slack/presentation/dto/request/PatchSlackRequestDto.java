package takeoff.logistics_service.msa.slack.presentation.dto.request;


import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PatchContentsResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record PatchSlackRequestDto(Long userId,
                                   PatchContentsRequestDto patchContentsRequestDto) {

    public Slack toEntity() {
        return Slack.builder()
            .userId(userId)
            .contents(patchContentsRequestDto.toVo())
            .build();
    }

}

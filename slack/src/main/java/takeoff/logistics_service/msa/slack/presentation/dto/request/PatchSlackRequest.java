package takeoff.logistics_service.msa.slack.presentation.dto.request;


import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record PatchSlackRequest(@NotNull Long userId,
                                @NotNull PatchContentsRequest patchContentsRequest) {

    public Slack toEntity() {
        return Slack.builder()
            .userId(userId)
            .contents(patchContentsRequest.toVo())
            .build();
    }

}

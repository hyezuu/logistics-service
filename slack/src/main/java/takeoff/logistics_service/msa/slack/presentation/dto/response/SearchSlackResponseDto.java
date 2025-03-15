package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchSlackResponseDto(UUID slackId,
                                     UUID userId,
                                     SearchContentsResponseDto searchContentsResponseDto) {


    public static SearchSlackResponseDto from(Slack slack) {
        return SearchSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getId())
            .searchContentsResponseDto(SearchContentsResponseDto.from(slack.getContents()))
            .build();
    }


}

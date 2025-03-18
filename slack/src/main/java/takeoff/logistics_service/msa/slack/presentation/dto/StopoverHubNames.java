package takeoff.logistics_service.msa.slack.presentation.dto;

import java.util.List;
import takeoff.logistics_service.msa.slack.application.dto.StopoverHubNamesDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 16.
 */
public record StopoverHubNames(List<String> hubNames) {

    public StopoverHubNamesDto toApplicationDto() {
        return StopoverHubNamesDto.builder()
            .hubNames(hubNames())
            .build();
    }
}

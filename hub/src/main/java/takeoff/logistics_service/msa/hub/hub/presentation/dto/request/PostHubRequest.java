package takeoff.logistics_service.msa.hub.hub.presentation.dto.request;

import takeoff.logistics_service.msa.hub.hub.application.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.LocationApi;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */

public record PostHubRequest(String hubName,
                             LocationApi locationApi) {

    public PostHubRequestDto toApplication() {
        return PostHubRequestDto.builder()
            .hubName(hubName)
            .locationDto(locationApi.toApplicationDto())
            .build();
    }
}

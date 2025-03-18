package takeoff.logistics_service.msa.hub.hub.presentation.dto.request;

import takeoff.logistics_service.msa.hub.hub.application.dto.request.PatchHubRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */

public record PatchHubRequest(String hubName) {

    public PatchHubRequestDto toApplication() {
        return PatchHubRequestDto.builder()
            .hubName(hubName)
            .build();
    }

}

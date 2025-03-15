package takeoff.logistics_service.msa.hub.hub.presentation.dto.request;

import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.LocationDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */

public record SearchHubRequestDto(String hubName) {

    public Hub toEntity() {
        return Hub.builder()
            .hubName(hubName)
            .build();
    }

}

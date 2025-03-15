package takeoff.logistics_service.msa.hub.hub.presentation.dto.request;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.model.entity.Location;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.LocationDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */

public record PostHubRequestDto(String hubName,
                                LocationDto locationDto) {

    public Hub toEntity() {
        return Hub.builder()
            .hubName(hubName)
            .location(locationDto.toVo())
            .build();
    }

}

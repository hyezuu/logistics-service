package takeoff.logistics_service.msa.hub.hub.application.dto.request;

import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.LocationDto;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PostHubRequestDto(String hubName,
                                LocationDto locationDto) {

    public Hub toEntity() {
        return Hub.builder()
            .hubName(hubName)
            .location(locationDto.toVo())
            .build();
    }

}

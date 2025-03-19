package takeoff.logistics_service.msa.hub.hub.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record GetRouteResponseDto(UUID hubId,
                                  String hubName,
                                  String address,
                                  Double latitude,
                                  Double longitude) {

    public static GetRouteResponseDto from(Hub hub) {
        return GetRouteResponseDto.builder()
            .hubId(hub.getId())
            .hubName(hub.getHubName())
            .address(hub.getHubName())
            .latitude(hub.getLocation().getLatitude())
            .longitude(hub.getLocation().getLongitude())
            .build();
    }

}

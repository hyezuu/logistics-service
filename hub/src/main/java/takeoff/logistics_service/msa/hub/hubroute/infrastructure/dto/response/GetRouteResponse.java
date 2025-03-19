package takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
public record GetRouteResponse(UUID hubId,
                               String hubName,
                               String address,
                               Double latitude,
                               Double longitude) {

    public GetRouteResponseDto from() {
        return GetRouteResponseDto.builder()
            .hubId(hubId)
            .hubName(hubName)
            .address(address)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }

}

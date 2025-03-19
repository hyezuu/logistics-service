package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record GetRouteResponse(UUID hubId,
                               String hubName,
                               String address,
                               Double latitude,
                               Double longitude) {

    public static GetRouteResponse from(GetRouteResponseDto getRouteResponseDto) {
        return GetRouteResponse.builder()
            .hubId(getRouteResponseDto.hubId())
            .hubName(getRouteResponseDto.hubName())
            .address(getRouteResponseDto.address())
            .latitude(getRouteResponseDto.latitude())
            .longitude(getRouteResponseDto.longitude())
            .build();
    }

}

package takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@Builder
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
    //테스트용
    public static GetRouteResponse fromTest(GetRouteResponseDto getRouteResponseDto) {
        return GetRouteResponse.builder()
            .hubId(getRouteResponseDto.hubId())
            .hubName(getRouteResponseDto.hubName())
            .address(getRouteResponseDto.address())
            .latitude(getRouteResponseDto.latitude())
            .longitude(getRouteResponseDto.longitude())
            .build();
    }

}

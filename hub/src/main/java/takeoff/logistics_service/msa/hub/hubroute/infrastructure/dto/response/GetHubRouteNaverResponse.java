package takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response;

import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteNaverResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
public record GetHubRouteNaverResponse(Integer distance,
                                       Integer duration) {

    public static GetHubRouteNaverResponseDto from(GetHubRouteNaverResponse getHubRouteNaverResponse) {
        return GetHubRouteNaverResponseDto.builder()
            .distance(getHubRouteNaverResponse.distance())
            .duration(getHubRouteNaverResponse.duration())
            .build();
    }

}

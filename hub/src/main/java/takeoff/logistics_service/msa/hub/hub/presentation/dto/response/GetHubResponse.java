package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record GetHubResponse(UUID hubId,
                             String hubName,
                             String address,
                             Double latitude,
                             Double longitude) {

    public static GetHubResponse from(GetHubResponseDto getHubResponseDto) {
        return GetHubResponse.builder()
            .hubId(getHubResponseDto.hubId())
            .hubName(getHubResponseDto.hubName())
            .address(getHubResponseDto.address())
            .latitude(getHubResponseDto.latitude())
            .longitude(getHubResponseDto.longitude())
            .build();
    }

}

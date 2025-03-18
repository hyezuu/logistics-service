package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PostHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PostHubResponse(UUID hubId,
                              String hubName,
                              String address,
                              Double latitude,
                              Double longitude) {

    public static PostHubResponse from(PostHubResponseDto postHubResponseDto) {
        return PostHubResponse.builder()
            .hubId(postHubResponseDto.hubId())
            .hubName(postHubResponseDto.hubName())
            .address(postHubResponseDto.address())
            .latitude(postHubResponseDto.latitude())
            .longitude(postHubResponseDto.longitude())
            .build();
    }

}

package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PatchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PatchHubResponse(UUID hubId,
                               String hubName,
                               String address,
                               Double latitude,
                               Double longitude) {
    public static PatchHubResponse from(PatchHubResponseDto patchHubResponseDto) {
        return PatchHubResponse.builder()
            .hubId(patchHubResponseDto.hubId())
            .hubName(patchHubResponseDto.hubName())
            .address(patchHubResponseDto.address())
            .latitude(patchHubResponseDto.latitude())
            .longitude(patchHubResponseDto.longitude())
            .build();
    }

}

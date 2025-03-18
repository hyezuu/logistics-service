package takeoff.logistics_service.msa.hub.hub.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record GetHubResponseDto(UUID hubId,
                                String hubName,
                                String address,
                                Double latitude,
                                Double longitude) {

    public static GetHubResponseDto from(Hub hub) {
        return GetHubResponseDto.builder()
            .hubId(hub.getId())
            .hubName(hub.getHubName())
            .address(hub.getLocation().getAddress())
            .latitude(hub.getLocation().getLatitude())
            .longitude(hub.getLocation().getLongitude())
            .build();
    }

}

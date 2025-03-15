package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record SearchHubResponseDto(UUID hubId,
                                   String hubName,
                                   String address,
                                   Double latitude,
                                   Double longitude) {
    public static SearchHubResponseDto from(Hub hub) {
        return SearchHubResponseDto.builder()
            .hubId(hub.getId())
            .hubName(hub.getHubName())
            .address(hub.getLocation().getAddress())
            .latitude(hub.getLocation().getLatitude())
            .longitude(hub.getLocation().getLongitude())
            .build();
    }

}

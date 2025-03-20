package takeoff.logistics_service.msa.hub.hub.application.dto.feign;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record GetAllHubsDto(UUID hubId,
                            String hubName,
                            String address,
                            Double latitude,
                            Double longitude) {

    public static GetAllHubsDto from(Hub hub) {
        return GetAllHubsDto.builder()
            .hubId(hub.getId())
            .hubName(hub.getHubName())
            .address(hub.getLocation().getAddress())
            .latitude(hub.getLocation().getLatitude())
            .longitude(hub.getLocation().getLongitude())
            .build();
    }

}

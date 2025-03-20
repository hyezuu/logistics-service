package takeoff.logistics_service.msa.hub.hub.presentation.dto.feign;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.feign.GetAllHubsDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
@Builder
public record GetAllHubs(UUID hubId,
                         String hubName,
                         String address,
                         Double latitude,
                         Double longitude) {

    public static GetAllHubs from(GetAllHubsDto getAllHubsDto) {
        return GetAllHubs.builder()
            .hubId(getAllHubsDto.hubId())
            .hubName(getAllHubsDto.hubName())
            .address(getAllHubsDto.address())
            .latitude(getAllHubsDto.latitude())
            .longitude(getAllHubsDto.longitude())
            .build();
    }

}

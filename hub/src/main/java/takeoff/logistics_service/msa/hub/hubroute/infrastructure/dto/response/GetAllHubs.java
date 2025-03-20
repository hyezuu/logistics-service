package takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubAllListResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
public record GetAllHubs(UUID hubId,
                         String hubName,
                         String address,
                         Double latitude,
                         Double longitude) {

    public static HubAllListResponseDto from(GetAllHubs getAllHubs) {
        return HubAllListResponseDto.builder()
            .hubId(getAllHubs.hubId())
            .hubName(getAllHubs.hubName())
            .address(getAllHubs.address())
            .latitude(getAllHubs.latitude())
            .longitude(getAllHubs.longitude())
            .build();
    }

}

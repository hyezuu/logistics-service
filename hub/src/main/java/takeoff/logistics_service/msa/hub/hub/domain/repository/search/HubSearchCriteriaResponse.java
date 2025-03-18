package takeoff.logistics_service.msa.hub.hub.domain.repository.search;


import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Builder
public record HubSearchCriteriaResponse(UUID hubId,
                                        String hubName,
                                        String address,
                                        Double latitude,
                                        Double longitude) {

    public static HubSearchCriteriaResponse from(Hub hub) {
        return HubSearchCriteriaResponse.builder()
            .hubId(hub.getId())
            .hubName(hub.getHubName())
            .address(hub.getLocation().getAddress())
            .latitude(hub.getLocation().getLatitude())
            .longitude(hub.getLocation().getLongitude())
            .build();
    }

}

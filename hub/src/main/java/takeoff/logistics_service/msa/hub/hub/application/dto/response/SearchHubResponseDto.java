package takeoff.logistics_service.msa.hub.hub.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteriaResponse;

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
    public static SearchHubResponseDto from(HubSearchCriteriaResponse hubSearchCriteriaResponse) {
        return SearchHubResponseDto.builder()
            .hubId(hubSearchCriteriaResponse.hubId())
            .hubName(hubSearchCriteriaResponse.hubName())
            .address(hubSearchCriteriaResponse.address())
            .latitude(hubSearchCriteriaResponse.latitude())
            .longitude(hubSearchCriteriaResponse.longitude())
            .build();
    }

}

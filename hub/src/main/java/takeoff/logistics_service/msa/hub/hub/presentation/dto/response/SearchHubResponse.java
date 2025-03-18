package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.SearchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record SearchHubResponse(UUID hubId,
                                String hubName,
                                String address,
                                Double latitude,
                                Double longitude) {
    public static SearchHubResponse from(SearchHubResponseDto searchHubResponseDto) {
        return SearchHubResponse.builder()
            .hubId(searchHubResponseDto.hubId())
            .hubName(searchHubResponseDto.hubName())
            .address(searchHubResponseDto.address())
            .latitude(searchHubResponseDto.latitude())
            .longitude(searchHubResponseDto.longitude())
            .build();
    }

}

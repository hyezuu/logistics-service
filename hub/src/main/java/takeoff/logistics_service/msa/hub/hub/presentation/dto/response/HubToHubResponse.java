package takeoff.logistics_service.msa.hub.hub.presentation.dto.response;

import java.util.List;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.HubToHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 24.
 */
@Builder
public record HubToHubResponse(List<GetRouteResponse> hubToHubList) {

    public static HubToHubResponse from(HubToHubResponseDto hubToHubResponseDto) {
        return HubToHubResponse.builder()
            .hubToHubList(
                hubToHubResponseDto.hubToHubList()
                    .stream().map(GetRouteResponse::from)
                    .toList()
            )
            .build();
    }

}

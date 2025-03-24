package takeoff.logistics_service.msa.hub.hub.application.dto.response;

import java.util.List;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 24.
 */
@Builder
public record HubToHubResponseDto(List<GetRouteResponseDto> hubToHubList) {

    public static HubToHubResponseDto from(List<GetRouteResponseDto> hubToHubList) {
        return HubToHubResponseDto.builder()
            .hubToHubList(hubToHubList)
            .build();
    }

}

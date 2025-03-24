package takeoff.logistics_service.msa.hub.hub.presentation.dto.feign;

import java.util.List;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hub.application.dto.feign.AllHubListResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 25.
 */
@Builder
public record AllHubListResponse(List<GetAllHubs> getAllHubsList) {

    public static AllHubListResponse from(AllHubListResponseDto allHub) {
        return AllHubListResponse.builder()
            .getAllHubsList(allHub.hubsDtoList().stream().map(GetAllHubs::from).toList())
            .build();
    }
}

package takeoff.logistics_service.msa.hub.hubroute.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubRoutesDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@Builder
public record HubRoutes(List<FindHubRoutes> hubAllListResponseList) {

    public static HubRoutes from(HubRoutesDto hubRoutesDto) {
        List<FindHubRoutes> findHubRoutesList = hubRoutesDto.hubRoutesDtoList().stream()
            .map(FindHubRoutes::from)
            .collect(Collectors.toList());

        return new HubRoutes(findHubRoutesList);
    }

}

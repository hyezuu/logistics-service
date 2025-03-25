package takeoff.logistics_service.msa.hub.hubroute.application.service.client;

import java.util.List;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubAllListResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.HubIdsDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
public interface HubClient {
    List<GetRouteResponseDto> findByToHubIdAndFromHubId(HubIdsDto hubIdsDto);
    List<HubAllListResponseDto> findAllHubs();

}

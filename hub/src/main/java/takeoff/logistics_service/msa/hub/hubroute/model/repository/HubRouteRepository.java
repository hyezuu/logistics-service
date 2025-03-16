package takeoff.logistics_service.msa.hub.hubroute.model.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.model.entity.HubRoute;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.GetHubRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubRouteRepository {

    Optional<HubRoute> findById(UUID hubRouteId);
}

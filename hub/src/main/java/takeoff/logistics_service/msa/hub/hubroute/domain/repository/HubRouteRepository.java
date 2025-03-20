package takeoff.logistics_service.msa.hub.hubroute.domain.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubRouteRepository {

    Optional<HubRoute> findById(UUID hubRouteId);

    HubRoute save(HubRoute hubRoute);
    Optional<HubRoute> findByFromHubIdAndToHubId(UUID fromHubId, UUID toHubId);
}

package takeoff.logistics_service.msa.hub.hubroute.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;
import takeoff.logistics_service.msa.hub.hubroute.domain.repository.HubRouteRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface JpaHubRouteRepository extends JpaRepository<HubRoute, UUID>, HubRouteRepository {
    Optional<HubRoute> findByFromHubIdAndToHubId(UUID fromHubId, UUID toHubId);
}

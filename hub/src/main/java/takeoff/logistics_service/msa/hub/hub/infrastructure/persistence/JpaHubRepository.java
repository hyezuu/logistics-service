package takeoff.logistics_service.msa.hub.hub.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.model.repository.HubRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface JpaHubRepository extends JpaRepository<Hub, UUID>, HubRepository,JpaHubRepositoryCustom {

}

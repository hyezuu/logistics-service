package takeoff.logistics_service.msa.hub.hub.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.domain.repository.HubRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface JpaHubRepository extends JpaRepository<Hub, UUID>, HubRepository,JpaHubRepositoryCustom {

    Optional<Hub> findByIdAndDeletedAtIsNull(UUID hubId);

    List<Hub> findByIdInAndDeletedAtIsNull(List<UUID> ids);

    List<Hub> findByDeletedAtIsNull();
}

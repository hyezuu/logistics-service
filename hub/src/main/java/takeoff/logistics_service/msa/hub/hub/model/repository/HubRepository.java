package takeoff.logistics_service.msa.hub.hub.model.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubRepository {

    Hub save(Hub hub);

    Optional<Hub> findByHubId(UUID hubId);
}

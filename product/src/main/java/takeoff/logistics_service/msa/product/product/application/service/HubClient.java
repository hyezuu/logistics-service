package takeoff.logistics_service.msa.product.product.application.service;

import java.util.UUID;

public interface HubClient {

	void findByHubId(UUID hubId);
}

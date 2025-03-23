package takeoff.logistics_service.msa.product.stock.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logistics_service.msa.product.stock.infrastructure.client.dto.response.GetManagerListResponse;

@FeignClient(name = "user", contextId = "stock-user-client", configuration = FeignClientConfig.class)
public interface FeignUserClient {

	@GetMapping("/api/v1/app/users/managers/hub/users")
	GetManagerListResponse getUsersByHubManager (@RequestParam Long managerId);
}

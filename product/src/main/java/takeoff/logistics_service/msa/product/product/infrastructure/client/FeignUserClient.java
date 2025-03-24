package takeoff.logistics_service.msa.product.product.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response.GetManagerListResponse;

@FeignClient(name = "user", contextId = "product-user-client", configuration = FeignClientConfig.class)
public interface FeignUserClient {

	@GetMapping("/api/v1/app/users/managers/company/users")
	GetManagerListResponse getUsersByCompanyManager (@RequestParam Long managerId);
}

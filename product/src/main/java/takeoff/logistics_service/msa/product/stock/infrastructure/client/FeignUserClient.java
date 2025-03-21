package takeoff.logistics_service.msa.product.stock.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import takeoff.logistics_service.msa.product.stock.infrastructure.client.dto.response.GetUserResponse;

@FeignClient(name = "user", contextId = "stock-user-client")
public interface FeignUserClient {

	@GetMapping("/api/v1/app/users/{userId}")
	GetUserResponse findByUserId (@PathVariable Long userId);
}

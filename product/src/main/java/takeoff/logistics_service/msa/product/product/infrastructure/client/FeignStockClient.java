package takeoff.logistics_service.msa.product.product.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request.PostStockRequest;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response.PostStockResponse;

@FeignClient(name = "product", configuration = FeignClientConfig.class)
public interface FeignStockClient {

	@PostMapping("/api/v1/app/stock")
	PostStockResponse saveStock(@RequestBody PostStockRequest requestDto);

	@DeleteMapping("/api/v1/app/stock/all-by-product")
	void deleteStock(@RequestParam UUID productId);
}

package takeoff.logistics_service.msa.product.product.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response.GetCompanyResponse;

@FeignClient(name = "company", configuration = FeignClientConfig.class)
public interface FeignCompanyClient {

	@GetMapping("/api/v1/app/companies/{companyId}")
	GetCompanyResponse findByCompanyId(@PathVariable UUID companyId);
}

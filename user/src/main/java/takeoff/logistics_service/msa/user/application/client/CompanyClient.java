package takeoff.logistics_service.msa.user.application.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logistics_service.msa.user.application.dto.GetCompanyFeignResponse;

@FeignClient(name = "company", path = "/api/v1/app/companies", configuration = FeignClientConfig.class)
public interface CompanyClient {
    @GetMapping("/{companyId}")
    GetCompanyFeignResponse findCompanyById(@PathVariable("companyId") UUID companyId);
}

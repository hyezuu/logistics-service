package takeoff.logistics_service.msa.order.application.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import takeoff.logistics_service.msa.order.application.client.dto.response.GetCompanyResponseDto;

@Component
@FeignClient(name = "company")
public interface CompanyClient {

  @GetMapping("/api/v1/companies/{companyId}")
  public GetCompanyResponseDto findByCompanyId(@PathVariable(name = "companyId") UUID companyId);
}

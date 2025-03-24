package takeoff.logistics_service.msa.company.application.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub", url = "/api/vi/app/hubs")
public interface HubInternalClient {
    @GetMapping("/api/v1/app/hubs/{hubId}")
    void checkHubExists(@PathVariable("hubId") UUID hubId);
}

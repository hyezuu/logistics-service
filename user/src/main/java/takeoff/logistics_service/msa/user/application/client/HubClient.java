package takeoff.logistics_service.msa.user.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import takeoff.logistics_service.msa.common.config.FeignClientConfig;
import takeoff.logistics_service.msa.user.application.dto.GetHubFeignResponse;

import java.util.UUID;

@FeignClient(name = "hub", path = "/api/v1/app/hubs", configuration = FeignClientConfig.class)
public interface HubClient {
    @GetMapping("/{hubId}")
    GetHubFeignResponse findHubById(@PathVariable("hubId") UUID hubId);
}

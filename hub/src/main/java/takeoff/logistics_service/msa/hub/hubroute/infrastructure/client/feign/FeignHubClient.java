package takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.request.HubIds;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response.GetRouteResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@FeignClient(name = "hub", url = "http://localhost:19042")
public interface FeignHubClient {

    @PostMapping("/api/v1/app/hubs/stopover")
    List<GetRouteResponse> findByToHubIdAndFromHubId(@RequestBody HubIds hubIds);

}

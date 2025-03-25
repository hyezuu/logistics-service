package takeoff.logistics_service.msa.hub.hubroute.application.service.client;

import java.util.List;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteNaverResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */

public interface NaverRequestClient {

    Mono<GetHubRouteNaverResponseDto> sendRequestToNaver(List<GetRouteResponseDto> responseToHub);
}

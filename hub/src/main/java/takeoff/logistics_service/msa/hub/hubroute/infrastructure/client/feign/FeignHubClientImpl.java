package takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.feign;

import feign.FeignException.FeignClientException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.HubAllListResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.HubIdsDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteBusinessException;
import takeoff.logistics_service.msa.hub.hubroute.application.exception.HubRouteErrorCode;
import takeoff.logistics_service.msa.hub.hubroute.application.service.client.HubClient;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.request.HubIds;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response.GetAllHubs;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.dto.response.GetRouteResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class FeignHubClientImpl implements HubClient {

    private final FeignHubClient feignHubClient;

    @Override
    public List<GetRouteResponseDto> findByToHubIdAndFromHubId(HubIdsDto hubIdsDto) {
        try {
            return feignHubClient.findByToHubIdAndFromHubId(HubIds.from(hubIdsDto))
                .stream()
                .map(GetRouteResponse::from)
                .toList();
        } catch (FeignClientException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public List<HubAllListResponseDto> findAllHubs() {
        try {
            return feignHubClient.findAllHubs()
                .stream()
                .map(GetAllHubs::from)
                .toList();
        } catch (FeignClientException e) {
            throw handleFeignException(e);
        }
    }

    private BusinessException handleFeignException(FeignClientException e) {
        if (e.status() == HttpStatus.BAD_REQUEST.value()) {
            return HubRouteBusinessException.from(HubRouteErrorCode.INVALID_HUB_ROUTE_REQUEST);
        } else if (e.status() == HttpStatus.NOT_FOUND.value()) {
            return HubRouteBusinessException.from(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND);
        } else {
            return HubRouteBusinessException.from(CommonErrorCode.BAD_GATEWAY);
        }
    }

}

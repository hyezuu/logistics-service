package takeoff.logistics_service.msa.hub.hubroute.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteService;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.HubRoutes;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PostDeliveryHubRouteRequest;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PostHubRouteRequest;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.PostHubRouteResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/hubRoutes")
public class HubRouteInternalController {

    private final HubRouteService hubRouteService;

    @PostMapping
    @RoleCheck(roles = {UserRole.MASTER_ADMIN,UserRole.HUB_DELIVERY_MANAGER})
    public PostHubRouteResponse createHubRoute(@RequestBody PostHubRouteRequest request) {
        return PostHubRouteResponse.from(hubRouteService.createHubRoute(request.toApplication()));
    }

    @PostMapping("/delivery")
    public HubRoutes getDeliveryHubRouteList(@RequestBody PostDeliveryHubRouteRequest request) {
        return HubRoutes.from(hubRouteService.getDeliveryHubRouteList(request.toApplication()));
    }


}

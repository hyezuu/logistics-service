package takeoff.logistics_service.msa.hub.hub.presentation.internal;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.HubIds;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.feign.GetAllHubs;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.GetHubResponse;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.GetRouteResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequestMapping("/api/v1/app/hubs")
@RequiredArgsConstructor
public class HubInternalController {

    private final HubService hubService;

    @GetMapping("/{hubId}")
    @RoleCheck(roles = {
        UserRole.MASTER_ADMIN,UserRole.COMPANY_MANAGER,UserRole.COMPANY_DELIVERY_MANAGER,
        UserRole.HUB_MANAGER,UserRole.HUB_DELIVERY_MANAGER})
    public GetHubResponse findByHubId(@PathVariable("hubId") UUID hubId) {
        return GetHubResponse.from(hubService.findByHubId(hubId));
    }

    @PostMapping("/stopover")
    @RoleCheck(roles = {UserRole.MASTER_ADMIN,UserRole.HUB_MANAGER,UserRole.HUB_DELIVERY_MANAGER})
    public List<GetRouteResponse> findByToHubIdAndFromHubId(@RequestBody HubIds hubIds) {
        return hubService.findByToHubIdAndFromHubId(hubIds.toApplicationDto()).stream()
            .map(GetRouteResponse::from)
            .toList();
    }

    @GetMapping("/allHub")
    @RoleCheck(roles = {
        UserRole.MASTER_ADMIN,UserRole.COMPANY_MANAGER,UserRole.COMPANY_DELIVERY_MANAGER,
        UserRole.HUB_MANAGER,UserRole.HUB_DELIVERY_MANAGER})
    public List<GetAllHubs> findByAllHub() {
        return (hubService.findAllHub().stream()
            .map(GetAllHubs::from)
            .toList());
    }
}

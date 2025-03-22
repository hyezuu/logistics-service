package takeoff.logistics_service.msa.hub.hubroute.presentation.external;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteService;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PutHubRouteRequest;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.GetHubRouteResponse;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.PutHubRouteResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubRoutes")
public class HubRouteExternalController {

    private final HubRouteService hubRouteService;


    @GetMapping("/{hubRouteId}")
    @RoleCheck(roles = {
        UserRole.MASTER_ADMIN,UserRole.COMPANY_MANAGER,UserRole.COMPANY_DELIVERY_MANAGER,
        UserRole.HUB_MANAGER,UserRole.HUB_DELIVERY_MANAGER})
    public ResponseEntity<GetHubRouteResponse> findByHubRoute(@PathVariable("hubRouteId")UUID hubRouteId) {
        return ResponseEntity.ok(GetHubRouteResponse.from(hubRouteService.findByHubRoute(hubRouteId)));
    }

    @PutMapping("/{hubRouteId}")
    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    public ResponseEntity<PutHubRouteResponse> updateHubRoute(@PathVariable("hubRouteId")UUID hubRouteId,
        @RequestBody PutHubRouteRequest requestDto) {
        return ResponseEntity.ok(PutHubRouteResponse.from(hubRouteService.updateHubRoute(hubRouteId, requestDto.toApplicationDto())));
    }
    @DeleteMapping("/{hubRouteId}")
    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    public ResponseEntity<Void> deleteHubRoute(
        @PathVariable("hubRouteId")UUID hubRouteId,
        @UserInfo UserInfoDto userInfo) {
        hubRouteService.deleteHubRoute(hubRouteId,userInfo.userId());
        return ResponseEntity.noContent().build();
    }
}

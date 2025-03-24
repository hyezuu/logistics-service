package takeoff.logistics_service.msa.hub.hub.presentation.external;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PatchHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.SearchHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PaginatedResultHubResponse;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PatchHubResponse;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubExternalController {

    private final HubService hubService;

    @PostMapping
    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    public ResponseEntity<PostHubResponse> saveHub(@RequestBody PostHubRequest request) {
        return ResponseEntity.ok(
            PostHubResponse.from(
            hubService.saveHub(request.toApplication())));
    }

    @PatchMapping("/{hubId}")
    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    public ResponseEntity<PatchHubResponse> updateHub(@PathVariable("hubId") UUID hubId,
        @RequestBody PatchHubRequest request) {
        return ResponseEntity.ok(
            PatchHubResponse.from(
                hubService.updateHub(hubId,
                    request.toApplication())));
    }

    @DeleteMapping("/{hubId}")
    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    public ResponseEntity<Void> deleteHub(
        @PathVariable("hubId") UUID hubId,
        @UserInfo UserInfoDto userInfo) {
        hubService.deleteHub(hubId,userInfo.userId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @RoleCheck(roles = {
        UserRole.MASTER_ADMIN,UserRole.COMPANY_MANAGER,UserRole.COMPANY_DELIVERY_MANAGER,
        UserRole.HUB_MANAGER,UserRole.HUB_DELIVERY_MANAGER})
    public ResponseEntity<PaginatedResultHubResponse> searchHub(SearchHubRequest request) {
        return ResponseEntity.ok(PaginatedResultHubResponse.from(hubService.searchHub(request.toApplicationDto())));
    }

}

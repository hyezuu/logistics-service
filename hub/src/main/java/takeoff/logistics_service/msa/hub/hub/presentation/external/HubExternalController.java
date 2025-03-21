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
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.PaginatedResultApi;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PatchHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.SearchHubRequest;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PatchHubResponse;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponse;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.SearchHubResponse;

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
    public ResponseEntity<PostHubResponse> saveHub(@RequestBody PostHubRequest request) {
        return ResponseEntity.ok(
            PostHubResponse.from(
            hubService.saveHub(request.toApplication())));
    }

    @PatchMapping("/{hubId}")
    public ResponseEntity<PatchHubResponse> updateHub(@PathVariable("hubId") UUID hubId,
        @RequestBody PatchHubRequest request) {
        return ResponseEntity.ok(
            PatchHubResponse.from(
                hubService.updateHub(hubId,
                    request.toApplication())));
    }

    @DeleteMapping("/{hubId}")
    public ResponseEntity<Void> deleteHub(@PathVariable("hubId") UUID hubId) {
        hubService.deleteHub(hubId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResultApi<SearchHubResponse>> searchHub(SearchHubRequest request) {
        return ResponseEntity.ok(PaginatedResultApi.from(hubService.searchHub(request.toApplicationDto())));
    }

}

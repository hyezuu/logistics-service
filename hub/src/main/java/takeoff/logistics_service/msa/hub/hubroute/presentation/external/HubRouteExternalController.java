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
    public ResponseEntity<GetHubRouteResponse> findByHubRoute(@PathVariable("hubRouteId")UUID hubRouteId) {
        return ResponseEntity.ok(GetHubRouteResponse.from(hubRouteService.findByHubRoute(hubRouteId)));
    }

    @PutMapping("/{hubRouteId}")
    public ResponseEntity<PutHubRouteResponse> updateHubRoute(@PathVariable("hubRouteId")UUID hubRouteId,
        @RequestBody PutHubRouteRequest requestDto) {
        return ResponseEntity.ok(PutHubRouteResponse.from(hubRouteService.updateHubRoute(hubRouteId, requestDto.toApplicationDto())));
    }
    @DeleteMapping("/{hubRouteId}/{userId}")
    public ResponseEntity<Void> deleteHubRoute(
        @PathVariable("hubRouteId")UUID hubRouteId,
        @PathVariable("userId") Long userId) {
        hubRouteService.deleteHubRoute(hubRouteId,userId);
        return ResponseEntity.noContent().build();
    }
}

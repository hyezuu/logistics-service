package takeoff.logistics_service.msa.hub.hubroute.presentation.external;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteService;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.request.PutHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response.PutHubRouteResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubRoutes")
public class HubRouteExternalController {

    private final HubRouteService hubRouteService;


    @GetMapping("{/hubRouteId}")
    public ResponseEntity<GetHubRouteResponseDto> findByHubRoute(@PathVariable("hubRouteId")UUID hubRouteId) {
        return ResponseEntity.ok(hubRouteService.findByHubRoute(hubRouteId));
    }

    @PutMapping("{/hubRouteId}")
    public ResponseEntity<PutHubRouteResponseDto> updateHubRoute(@PathVariable("hubRouteId")UUID hubRouteId,
        PutHubRouteRequestDto requestDto) {
        return ResponseEntity.ok(hubRouteService.updateHubRoute(hubRouteId, requestDto));
    }
    @DeleteMapping("{/hubRouteId}")
    public ResponseEntity<Void> deleteHubRoute(@PathVariable("hubRouteId")UUID hubRouteId) {
        hubRouteService.deleteHubRoute(hubRouteId);
        return ResponseEntity.noContent().build();
    }
}

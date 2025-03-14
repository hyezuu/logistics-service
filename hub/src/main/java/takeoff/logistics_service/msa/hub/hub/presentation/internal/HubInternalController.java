package takeoff.logistics_service.msa.hub.hub.presentation.internal;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.GetHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
public class HubInternalController {

    private final HubService hubService;

    @GetMapping("/{hubId}")
    public GetHubResponseDto findByHubId(@PathVariable("hubId")UUID hubId) {
        return hubService.findByHubId(hubId);
    }

}

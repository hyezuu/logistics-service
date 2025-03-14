package takeoff.logistics_service.msa.hub.hub.presentation.external;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PatchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PatchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
public class HubExternalController {

    private final HubService hubService;

    @PostMapping
    public ResponseEntity<PostHubResponseDto> saveHub(@RequestBody PostHubRequestDto requestDto) {
        return ResponseEntity.ok(hubService.saveHub(requestDto));
    }

    @PatchMapping("/{hubId}")
    public ResponseEntity<PatchHubResponseDto> updateHub(@PathVariable("hubId") UUID hubId,
        @RequestBody PatchHubRequestDto requestDto) {
        return ResponseEntity.ok(hubService.updateHub(hubId,requestDto));
    }
    //      Auditing 설정시 추가 개발 예정
//    @DeleteMapping("/{hubId}")
//    public void deleteHub(@PathVariable("hubId") UUID hubId) {
//        hubService.deleteHub(hubId);
//    }

}

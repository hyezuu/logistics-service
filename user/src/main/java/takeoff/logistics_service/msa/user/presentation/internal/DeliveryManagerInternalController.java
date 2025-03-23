package takeoff.logistics_service.msa.user.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.user.application.service.DeliveryManagerService;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerListInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerListInternalResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerInternalController {

    private final DeliveryManagerService deliveryManagerService;

    @GetMapping("/{id}")
    public GetDeliveryManagerResponseDto getDeliveryManagerById(@PathVariable Long id) {
        return deliveryManagerService.getDeliveryManagerById(id);
    }

    @GetMapping("/company")
    public GetDeliveryManagerListInternalResponseDto getCompanyDeliveryManagersByHubId(
            @RequestParam UUID hubId
    ) {
        List<GetDeliveryManagerListInfoDto> list = deliveryManagerService.getCompanyDeliveryManagersByHubId(hubId);
        return GetDeliveryManagerListInternalResponseDto.from(list);
    }

    @GetMapping("/hub")
    public GetDeliveryManagerListInternalResponseDto getAllHubDeliveryManagers() {
        List<GetDeliveryManagerListInfoDto> list = deliveryManagerService.getAllHubDeliveryManagers();
        return GetDeliveryManagerListInternalResponseDto.from(list);
    }


}

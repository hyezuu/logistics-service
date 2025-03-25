package takeoff.logistics_service.msa.user.presentation.internal;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.user.application.service.DeliveryManagerService;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerListInfoDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerListInternalResponseDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerResponseDto;

@RestController
@RequestMapping("/api/v1/app/users/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerInternalController {

    private final DeliveryManagerService deliveryManagerService;

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER, UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER})
    @GetMapping("/{id}")
    public GetDeliveryManagerResponseDto getDeliveryManagerById(
            @PathVariable Long id,
            @UserInfo UserInfoDto userInfoDto
            ) {
        return deliveryManagerService.getDeliveryManagerById(id, userInfoDto);
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
    @GetMapping("/company")
    public GetDeliveryManagerListInternalResponseDto getCompanyDeliveryManagersByHubId(
            @RequestParam UUID hubId,
            @UserInfo UserInfoDto userInfoDto
    ) {
        List<GetDeliveryManagerListInfoDto> list = deliveryManagerService.getCompanyDeliveryManagersByHubId(hubId, userInfoDto);
        return GetDeliveryManagerListInternalResponseDto.from(list);
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN})
    @GetMapping("/hub")
    public GetDeliveryManagerListInternalResponseDto getAllHubDeliveryManagers(
            @UserInfo UserInfoDto userInfoDto
    ) {
        List<GetDeliveryManagerListInfoDto> list = deliveryManagerService.getAllHubDeliveryManagers(userInfoDto);
        return GetDeliveryManagerListInternalResponseDto.from(list);
    }


}

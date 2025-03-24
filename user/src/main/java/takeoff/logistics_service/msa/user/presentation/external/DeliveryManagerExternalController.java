package takeoff.logistics_service.msa.user.presentation.external;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.user.application.service.DeliveryManagerService;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetDeliveryManagerListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

@RestController
@RequestMapping("/api/v1/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerExternalController {

    private final DeliveryManagerService deliveryManagerService;

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
    @PostMapping("/create")
    public ResponseEntity<PostDeliveryManagerResponseDto> createDeliveryManager(
            @Valid @RequestBody PostDeliveryManagerRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
            ) {
        return ResponseEntity.ok(deliveryManagerService.createDeliveryManager(requestDto, userInfoDto));
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
    @PatchMapping("/{id}")
    public ResponseEntity<PatchDeliveryManagerResponseDto> updateDeliveryManager(
            @PathVariable Long id,
            @Valid @RequestBody PatchDeliveryManagerRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        return ResponseEntity.ok(deliveryManagerService.updateDeliveryManager(id, requestDto, userInfoDto));
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDeliveryManagerResponseDto> deleteDeliveryManager(
            @PathVariable Long id,
            @UserInfo UserInfoDto userInfoDto
    ){
        return ResponseEntity.ok(deliveryManagerService.deleteDeliveryManager(id, userInfoDto));
    }


    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER, UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER})
    @GetMapping("/{id}")
    public ResponseEntity<GetDeliveryManagerResponseDto> getDeliveryManager(
            @PathVariable Long id,
            @UserInfo UserInfoDto userInfoDto
    ) {
        return ResponseEntity.ok(deliveryManagerService.getDeliveryManagerById(id, userInfoDto));
    }

    @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
    @GetMapping
    public ResponseEntity<GetDeliveryManagerListResponseDto> getDeliveryManagers(
            @ModelAttribute GetDeliveryManagerListRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        return ResponseEntity.ok(deliveryManagerService.getAllDeliveryManagers(requestDto, userInfoDto));
    }


}

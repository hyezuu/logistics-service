package takeoff.logisticsservice.msa.delivery.delivery.presentation.internal;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logisticsservice.msa.delivery.delivery.application.DeliveryService;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PatchDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PostDeliveryRequestDto;

@RestController
@RequestMapping("/api/v1/app/deliveries")
@RequiredArgsConstructor
public class DeliveryInternalController {

  private final DeliveryService deliveryService;

  @GetMapping
  public List<UUID> findAllDeliveryIdByUser(@RequestParam(name = "userId") Long userId) {
    return deliveryService.findAllDeliveryIdByUser(userId);
  }

  @PostMapping
  @RoleCheck
  public UUID saveDelivery(@RequestBody PostDeliveryRequestDto dto) {
    return deliveryService.saveDelivery(dto);
  }

  @PostMapping("/updateStatus")
  @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER,
      UserRole.COMPANY_DELIVERY_MANAGER})
  public void updateDeliveryStatus(@RequestBody PatchDeliveryRequestDto dto,
      @UserInfo UserInfoDto user) {
    deliveryService.updateDeliveryStatus(dto, user.userId(), user.role());
  }

  @DeleteMapping("/{deliveryId}")
  @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
  public void deleteDelivery(@PathVariable("deliveryId") UUID deliveryId,
      @UserInfo UserInfoDto user) {
    deliveryService.deleteDelivery(deliveryId, user.userId(), user.role());
  }

}

package takeoff.logisticsservice.msa.delivery.delivery.presentation.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logisticsservice.msa.delivery.delivery.application.DeliveryService;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.PaginatedResultApi;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.SearchDeliveryRequest;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.response.SearchDeliveryResponse;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryExternalController {

  private final DeliveryService deliveryService;

  @GetMapping("/search")
  @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.COMPANY_MANAGER, UserRole.HUB_MANAGER,
      UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER})
  public ResponseEntity<PaginatedResultApi<SearchDeliveryResponse>> searchDelivery(
      SearchDeliveryRequest request,
      @UserInfo UserInfoDto user
  ) {
    return ResponseEntity.ok()
        .body(PaginatedResultApi.from(deliveryService.searchDelivery(request.toApplicationDto(),
            user.userId(), user.role())));
  }
}

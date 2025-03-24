package takeoff.logistics_service.msa.order.presentation.external;


import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.order.application.service.OrderService;
import takeoff.logistics_service.msa.order.presentation.dto.PaginatedResultApi;
import takeoff.logistics_service.msa.order.presentation.dto.request.PatchOrderRequest;
import takeoff.logistics_service.msa.order.presentation.dto.request.PostOrderRequest;
import takeoff.logistics_service.msa.order.presentation.dto.request.SearchOrderRequest;
import takeoff.logistics_service.msa.order.presentation.dto.response.PatchOrderResponse;
import takeoff.logistics_service.msa.order.presentation.dto.response.PostOrderResponse;
import takeoff.logistics_service.msa.order.presentation.dto.response.SearchOrderResponse;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.COMPANY_MANAGER, UserRole.HUB_MANAGER,
      UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER})
  public ResponseEntity<PostOrderResponse> saveOrder(@RequestBody PostOrderRequest request) {
    return ResponseEntity.ok()
        .body(orderService.saveOrder(request));
  }


  @PatchMapping("/{orderId}")
  @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
  public ResponseEntity<PatchOrderResponse> updateOrder(
      @RequestBody PatchOrderRequest request,
      @PathVariable("orderId") UUID orderId) {
    return ResponseEntity.ok()
        .body(orderService.updateOrder(request, orderId));
  }

  @DeleteMapping("/{orderId}")
  @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
  public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") UUID orderId,
      @UserInfo UserInfoDto user) {
    orderService.deleteOrder(orderId, user.userId());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/search")
  @RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.COMPANY_MANAGER, UserRole.HUB_MANAGER,
      UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER})
  public ResponseEntity<PaginatedResultApi<SearchOrderResponse>> searchOrder(
      @ModelAttribute SearchOrderRequest request, @UserInfo UserInfoDto user
  ) {
    return ResponseEntity.ok()
        .body(PaginatedResultApi.from(orderService.searchOrder(
            request.toApplicationDto(), user.userId(), user.role()
        )));
  }

}

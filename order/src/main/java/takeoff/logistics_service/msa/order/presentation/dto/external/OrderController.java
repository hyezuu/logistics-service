package takeoff.logistics_service.msa.order.presentation.dto.external;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.order.application.service.OrderService;
import takeoff.logistics_service.msa.order.presentation.dto.request.PostOrderRequestDto;
import takeoff.logistics_service.msa.order.presentation.dto.response.PostOrderResponseDto;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<PostOrderResponseDto> saveOrder(@RequestBody PostOrderRequestDto dto) {
    return ResponseEntity.ok()
        .body(orderService.saveOrder(dto));
  }


}

package takeoff.logisticsservice.msa.delivery.presentation.internal;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logisticsservice.msa.delivery.application.DeliveryService;
import takeoff.logisticsservice.msa.delivery.presentation.dto.PostDeliveryRequestDto;

@RestController
@RequestMapping("/api/vi/app/deliveries")
@RequiredArgsConstructor
public class DeliveryInternalController {

  private final DeliveryService deliveryService;

  // TODO : 외부 사용자가 접근 못하도록 권한체크 + 마스터관리자만 접근가능한 앤드포인트
  @PostMapping
  public UUID saveDelivery(PostDeliveryRequestDto dto) {
    return deliveryService.saveDelivery(dto).id();
  }
}

package takeoff.logisticsservice.msa.delivery.presentation.internal;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logisticsservice.msa.delivery.application.DeliveryService;
import takeoff.logisticsservice.msa.delivery.presentation.dto.request.PatchDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.presentation.dto.request.PostDeliveryRequestDto;

@RestController
@RequestMapping("/api/vi/app/deliveries")
@RequiredArgsConstructor
public class DeliveryInternalController {

  private final DeliveryService deliveryService;

  // TODO : 외부 사용자가 접근 못하도록 권한체크 + 마스터관리자만 접근가능한 앤드포인트
  @PostMapping
  public UUID saveDelivery(@RequestBody PostDeliveryRequestDto dto) {
    return deliveryService.saveDelivery(dto);
  }

  // TODO : 외부 사용자가 접근 못하도록 권한체크 + 마스터관리자,허브관리자, 배송담당자만 접근가능한 앤드포인트
  @PostMapping("/updateStatus")
  public void updateDeliveryStatus(@RequestBody PatchDeliveryRequestDto dto) {
    deliveryService.updateDeliveryStatus(dto);
  }

  @DeleteMapping("/{deliveryId}")
  public void deleteDelivery(@PathVariable("deliveryId") UUID deliveryId) {
    deliveryService.deleteDelivery(deliveryId);
  }

}

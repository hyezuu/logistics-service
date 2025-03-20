package takeoff.logisticsservice.msa.delivery.delivery.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.request.GetDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.response.GetDeliveryResponseDto;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryId;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.DeliveryRepository;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PatchDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PostDeliveryRequestDto;


@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;

  // TODO : 분산 트랜잭션
  @Transactional
  public UUID saveDelivery(PostDeliveryRequestDto dto) {
    Delivery delivery = Delivery.builder()
        .orderId(dto.orderID())
        .build();

    deliveryRepository.save(delivery);

    return delivery.getId().getId();
  }

  @Transactional
  public void updateDeliveryStatus(PatchDeliveryRequestDto dto) {
    Delivery delivery = deliveryRepository.findById(DeliveryId.from(dto.deliveryId()))
        .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
    // TODO : 커스텀 예외로 변경

    delivery.modifyStatus(dto.status());
    // TODO : 카프카로 변경
  }

  @Transactional
  public void deleteDelivery(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(DeliveryId.from(deliveryId))
        .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
    // TODO : 커스텀 예외로 변경

    delivery.delete(1L);
    // TODO : 사용자 ID 를 받아와야 함
  }

  public GetDeliveryResponseDto searchDelivery(GetDeliveryRequestDto dto) {
    if (dto.deliveryId() != null) {
      Delivery delivery = deliveryRepository.findById(DeliveryId.from(dto.deliveryId()))
          .orElseThrow(() -> new IllegalArgumentException("[ERROR] 배송 정보를 찾을 수 없습니다."));

      return GetDeliveryResponseDto.from(delivery);

    }

    if (dto.orderId() != null) {
      Delivery delivery = deliveryRepository.findByOrderId(dto.orderId())
          .orElseThrow(() -> new IllegalArgumentException("[ERROR] 배송 정보를 찾을 수 없습니다."));

      return GetDeliveryResponseDto.from(delivery);
    }

    // TODO : 글로벌 예외로 변경

    return null;
  }
}

package takeoff.logisticsservice.msa.delivery.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logisticsservice.msa.delivery.model.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.model.repository.DeliveryRepository;
import takeoff.logisticsservice.msa.delivery.presentation.dto.PostDeliveryRequestDto;

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
}

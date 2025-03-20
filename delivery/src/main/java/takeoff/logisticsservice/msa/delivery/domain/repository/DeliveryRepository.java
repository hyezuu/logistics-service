package takeoff.logisticsservice.msa.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.domain.entity.DeliveryId;

public interface DeliveryRepository {

  Delivery save(Delivery delivery);

  Optional<Delivery> findById(DeliveryId deliveryId);

  Optional<Delivery> findByOrderId(UUID uuid);
}

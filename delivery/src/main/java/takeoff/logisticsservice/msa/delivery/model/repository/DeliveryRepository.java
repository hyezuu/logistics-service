package takeoff.logisticsservice.msa.delivery.model.repository;

import takeoff.logisticsservice.msa.delivery.model.entity.Delivery;

public interface DeliveryRepository {

  Delivery save(Delivery delivery);
}

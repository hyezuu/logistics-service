package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.repository;

import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;

public interface DeliveryRouteRepository {

  DeliveryRoute save(DeliveryRoute deliveryRoute);
}

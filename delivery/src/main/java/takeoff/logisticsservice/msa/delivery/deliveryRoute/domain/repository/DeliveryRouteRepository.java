package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.repository;

import java.util.List;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;

public interface DeliveryRouteRepository {

  DeliveryRoute save(DeliveryRoute deliveryRoute);

  List<DeliveryRoute> findAllByDeliveryId(UUID deliveryId);

  <S extends DeliveryRoute> Iterable<S> saveAll(Iterable<S> deliveryRoutes);
}

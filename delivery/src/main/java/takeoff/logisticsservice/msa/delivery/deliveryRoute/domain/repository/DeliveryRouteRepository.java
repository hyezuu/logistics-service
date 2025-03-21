package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;

public interface DeliveryRouteRepository {

  DeliveryRoute save(DeliveryRoute deliveryRoute);

  Collection<DeliveryRoute> saveAll(Collection<DeliveryRoute> deliveryRoutes);

  List<DeliveryRoute> findAllByDeliveryId(UUID deliveryId);
}

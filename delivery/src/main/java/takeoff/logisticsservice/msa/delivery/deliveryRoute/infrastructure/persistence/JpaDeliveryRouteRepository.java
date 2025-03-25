package takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRoute;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRouteId;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.repository.DeliveryRouteRepository;

public interface JpaDeliveryRouteRepository extends JpaRepository<DeliveryRoute, DeliveryRouteId>,
    DeliveryRouteRepository {

  List<DeliveryRoute> findAllByDeliveryManagerId(Long deliveryManagerId);
}


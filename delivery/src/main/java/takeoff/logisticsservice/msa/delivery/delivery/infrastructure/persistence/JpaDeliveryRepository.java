package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.DeliveryRepository;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID>, DeliveryRepository {

}

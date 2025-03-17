package takeoff.logisticsservice.msa.delivery.infrastructure;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logisticsservice.msa.delivery.model.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.model.repository.DeliveryRepository;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID>, DeliveryRepository {

}

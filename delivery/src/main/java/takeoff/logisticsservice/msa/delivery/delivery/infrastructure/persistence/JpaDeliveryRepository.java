package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryId;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.DeliveryRepository;
import takeoff.logisticsservice.msa.delivery.delivery.infrastructure.persistence.querydsl.JpaDeliveryRepositoryCustom;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, DeliveryId>,
    JpaDeliveryRepositoryCustom,
    DeliveryRepository {

}

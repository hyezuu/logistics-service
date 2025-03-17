package takeoff.logistics_service.msa.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.order.model.entity.Order;
import takeoff.logistics_service.msa.order.model.repository.OrderRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>,
    OrderRepository {

}

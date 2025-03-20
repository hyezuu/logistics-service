package takeoff.logistics_service.msa.order.domain.repository;

import java.util.Optional;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderId;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(OrderId orderId);


}

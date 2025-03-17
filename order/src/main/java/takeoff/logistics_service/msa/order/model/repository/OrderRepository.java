package takeoff.logistics_service.msa.order.model.repository;

import takeoff.logistics_service.msa.order.model.entity.Order;

public interface OrderRepository {

  Order save(Order order);
}

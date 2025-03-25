package takeoff.logistics_service.msa.order.domain.repository;

import java.util.Optional;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderId;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteria;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteriaResponse;
import takeoff.logistics_service.msa.order.domain.repository.search.PaginatedResult;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(OrderId orderId);

  PaginatedResult<OrderSearchCriteriaResponse> findAllBySearchParams(
      OrderSearchCriteria criteria
  );
}

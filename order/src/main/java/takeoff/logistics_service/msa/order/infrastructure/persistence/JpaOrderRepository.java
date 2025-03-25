package takeoff.logistics_service.msa.order.infrastructure.persistence;

import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderId;
import takeoff.logistics_service.msa.order.domain.entity.QOrder;
import takeoff.logistics_service.msa.order.domain.repository.OrderRepository;
import takeoff.logistics_service.msa.order.infrastructure.persistence.querydsl.JpaOrderRepositoryCustom;

public interface JpaOrderRepository extends
    JpaRepository<Order, OrderId>,
    QuerydslPredicateExecutor<Order>,
    QuerydslBinderCustomizer<QOrder>,
    JpaOrderRepositoryCustom,
    OrderRepository {


  @Override
  default void customize(QuerydslBindings bindings, QOrder root) {
    bindings.excludeUnlistedProperties(true);
    bindings.including(root.customerId, root.supplierId, root.createdAt);
    bindings.bind(root.customerId).first(SimpleExpression::eq);
    bindings.bind(root.supplierId).first(SimpleExpression::eq);
    bindings.bind(root.createdAt).first(ComparableExpression::goe);
  }

}

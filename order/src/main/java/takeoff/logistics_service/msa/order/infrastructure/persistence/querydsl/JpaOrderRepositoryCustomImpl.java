package takeoff.logistics_service.msa.order.infrastructure.persistence.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.QOrder;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteria;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteriaResponse;
import takeoff.logistics_service.msa.order.domain.repository.search.PaginatedResult;

public class JpaOrderRepositoryCustomImpl extends
    QuerydslRepositorySupport implements
    JpaOrderRepositoryCustom {

  public JpaOrderRepositoryCustomImpl() {
    super(Order.class);
  }

  @Override
  public PaginatedResult<OrderSearchCriteriaResponse> findAllBySearchParams(
      OrderSearchCriteria criteria) {
    QOrder order = QOrder.order;

    JPQLQuery<OrderSearchCriteriaResponse> query = from(order)
        .select(Projections.constructor(
            OrderSearchCriteriaResponse.class,
            order.id.orderId,
            order.supplierId,
            order.orderItems,
            order.customerId,
            order.address,
            order.requestNotes,
            order.createdAt,
            order.createdBy,
            order.updatedAt,
            order.updatedBy
        ))
        .where(
            customerIdEq(criteria.customerId()),
            deliveryIdIn(criteria.deliveryIds()),
            supplierIdEq(criteria.supplierId()),
            hubIdEq(criteria.hubId()),
            createdAtBetween(criteria.startDate(), criteria.endDate()),
            order.deletedAt.isNull()
        )
        .orderBy(createOrderSpecifier(criteria));

    Querydsl querydsl = getQuerydsl();
    Pageable pageable = convertToPageable(criteria);

    List<OrderSearchCriteriaResponse> result = Optional.of(
            querydsl.applyPagination(pageable, query))
        .orElseThrow(
            () -> new IllegalArgumentException("[Order-Querydsl] 페이징 정보 생성 오류"))
        .fetch();

    long total = query.fetchCount();

    return new PaginatedResult<>(result, criteria.page(), criteria.size(), total,
        (int) Math.ceil((double) total / criteria.size()));

  }

  private Pageable convertToPageable(OrderSearchCriteria criteria) {
    return PageRequest.of(criteria.page(), criteria.size());
  }

  private BooleanExpression customerIdEq(Long customerId) {
    return customerId != null ? QOrder.order.customerId.eq(customerId) : null;
  }

  private BooleanExpression deliveryIdIn(List<UUID> deliveryIds) {
    return deliveryIds != null ? QOrder.order.deliveryId.in(deliveryIds) : null;
  }

  private BooleanExpression hubIdEq(UUID hubId) {
    return hubId != null ? QOrder.order.managedHubId.eq(hubId) : null;
  }

  private BooleanExpression supplierIdEq(UUID supplierId) {
    return supplierId != null ? QOrder.order.supplierId.eq(supplierId) : null;
  }

  private BooleanExpression createdAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
    return QOrder.order.createdAt.between(startDate,
        endDate != null ? endDate : LocalDateTime.now());
  }

  private OrderSpecifier<?> createOrderSpecifier(OrderSearchCriteria criteria) {
    QOrder order = QOrder.order;

    if ("updatedAt".equals(criteria.sortBy())) {
      return criteria.isAsc() ? order.updatedAt.asc() : order.updatedAt.desc();
    } else {
      return criteria.isAsc() ? order.createdAt.asc() : order.createdAt.desc();
    }
  }

}

package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.persistence.querydsl;

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
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.QDelivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteria;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.DeliverySearchCriteriaResponse;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search.PaginatedResult;

public class JpaDeliveryRepositoryCustomImpl extends QuerydslRepositorySupport implements
    JpaDeliveryRepositoryCustom {

  public JpaDeliveryRepositoryCustomImpl() {
    super(Delivery.class);
  }

  @Override
  public PaginatedResult<DeliverySearchCriteriaResponse> findAllBySearchParams(
      DeliverySearchCriteria criteria) {

    QDelivery delivery = QDelivery.delivery;

    JPQLQuery<DeliverySearchCriteriaResponse> query = from(delivery)
        .select(Projections.constructor(
            DeliverySearchCriteriaResponse.class,
            delivery.id.id,
            delivery.orderId,
            delivery.status,
            delivery.deliveryManagerId,
            delivery.createdAt,
            delivery.createdBy,
            delivery.updatedAt,
            delivery.updatedBy
        ))
        .where(
            orderIdEq(criteria.orderId()),
            customerIdEq(criteria.customerId()),
            hubIdEq(criteria.hubId()),
            deliveryManagerIdEq(criteria.deliveryManagerId()),

            createdAtBetween(criteria.startDate(), criteria.endDate()),
            delivery.deletedAt.isNull()
        )
        .orderBy(createDeliverySpecifier(criteria));

    Querydsl querydsl = getQuerydsl();
    Pageable pageable = convertToPageable(criteria);

    List<DeliverySearchCriteriaResponse> result = Optional.of(
            querydsl.applyPagination(pageable, query))
        .orElseThrow(
            () -> new IllegalArgumentException("[Delivery-Querydsl] 페이징 정보 생성 오류"))
        .fetch();

    long total = query.fetchCount();

    return new PaginatedResult<>(result, criteria.page(), criteria.size(), total,
        (int) Math.ceil((double) total / criteria.size()));
  }

  private BooleanExpression orderIdEq(UUID orderId) {
    return orderId != null ? QDelivery.delivery.orderId.eq(orderId) : null;
  }

  private BooleanExpression hubIdEq(UUID hubId) {
    return hubId != null ? QDelivery.delivery.toHubId.eq(hubId) : null;
  }

  private BooleanExpression customerIdEq(Long customerId) {
    return customerId != null ? QDelivery.delivery.customerId.eq(customerId) : null;
  }

  private BooleanExpression deliveryManagerIdEq(Long deliveryManagerId) {
    return deliveryManagerId != null ? QDelivery.delivery.deliveryManagerId.eq(deliveryManagerId)
        : null;
  }

  private BooleanExpression createdAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
    return QDelivery.delivery.createdAt.between(startDate,
        endDate != null ? endDate : LocalDateTime.now());
  }

  private Pageable convertToPageable(DeliverySearchCriteria criteria) {
    return PageRequest.of(criteria.page(), criteria.size());
  }

  private OrderSpecifier<?> createDeliverySpecifier(DeliverySearchCriteria criteria) {
    QDelivery delivery = QDelivery.delivery;

    if ("updatedAt".equals(criteria.sortBy())) {
      return criteria.isAsc() ? delivery.updatedAt.asc() : delivery.updatedAt.desc();
    } else {
      return criteria.isAsc() ? delivery.createdAt.asc() : delivery.createdAt.desc();
    }
  }
}

package takeoff.logistics_service.msa.hub.hub.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.hub.hub.domain.entity.QHub.hub;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteria;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.HubSearchCriteriaResponse;
import takeoff.logistics_service.msa.hub.hub.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.hub.hub.infrastructure.persistence.JpaHubRepositoryCustom;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RequiredArgsConstructor
public class JpaHubRepositoryCustomImpl implements JpaHubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PaginatedResult<HubSearchCriteriaResponse> searchHub(HubSearchCriteria hubSearchCriteria) {

        List<Hub> fetch = queryFactory.select(hub)
            .from(hub)
            .where(
                containsHubName(hubSearchCriteria.hubName()),
                containsHubAddess(hubSearchCriteria.address()),
                hub.deletedAt.isNull()
            )
            .orderBy(getOrderSpecifier(hubSearchCriteria))
            .offset(hubSearchCriteria.page())
            .limit(hubSearchCriteria.size())
            .fetch();

        Long totalCount = queryFactory.select(hub.count())
            .from(hub)
            .where(
                containsHubName(hubSearchCriteria.hubName())
            )
            .fetchOne();

        if (totalCount == null) totalCount = 0L;

        List<HubSearchCriteriaResponse> resultList = fetch.stream()
            .map(HubSearchCriteriaResponse::from)
            .toList();

        int totalPages = (int) Math.ceil((double) totalCount / hubSearchCriteria.size());

        return new PaginatedResult<>(
            resultList,
            hubSearchCriteria.page(),
            hubSearchCriteria.size(),
            totalCount,
            totalPages);
    }

    private BooleanExpression containsHubName(String hubName) {
        return hubName == null ? null : hub.hubName.containsIgnoreCase(hubName);
    }
    private BooleanExpression containsHubAddess(String address) {
        return address == null ? null : hub.location.address.containsIgnoreCase(address);
    }

    private OrderSpecifier<?> getOrderSpecifier(HubSearchCriteria hubSearchCriteria) {
        return "updatedAt".equals(hubSearchCriteria.sortBy()) ? (
            hubSearchCriteria.isAsc() ? hub.updatedAt.asc() : hub.updatedAt.desc())
            : (hubSearchCriteria.isAsc() ? hub.createdAt.asc() : hub.createdAt.desc());
    }
}

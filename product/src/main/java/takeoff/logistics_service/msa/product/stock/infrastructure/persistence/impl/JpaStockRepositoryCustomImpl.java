package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.product.stock.domain.entity.QStock.stock;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockIdSearchCriteriaResponse;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteria;
import takeoff.logistics_service.msa.product.stock.domain.repository.search.StockSearchCriteriaResponse;
import takeoff.logistics_service.msa.product.stock.infrastructure.persistence.JpaStockRepositoryCustom;

@RequiredArgsConstructor
public class JpaStockRepositoryCustomImpl implements JpaStockRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public PaginatedResult<StockSearchCriteriaResponse> search(StockSearchCriteria criteria) {

		List<StockSearchCriteriaResponse> content = queryFactory
			.select(Projections.constructor(StockSearchCriteriaResponse.class,
				Projections.constructor(
					StockIdSearchCriteriaResponse.class, stock.id.productId, stock.id.hubId),
				stock.quantity,
				stock.updatedAt))
			.from(stock)
			.where(
				productIdContains(criteria.productId()),
				hubIdContains(criteria.hubId()),
				stock.deletedAt.isNull())
			.orderBy(getOrderSpecifier(criteria))
			.offset(criteria.page())
			.limit(criteria.size())
			.fetch();

		Long totalCount = queryFactory.select(stock.count())
			.from(stock)
			.where(
				productIdContains(criteria.productId()),
				hubIdContains(criteria.hubId()))
			.fetchOne();

		totalCount = totalCount == null ? 0 : totalCount;
		int totalPages = (int) Math.ceil((double) totalCount / criteria.size());

		return new PaginatedResult<>(
			content,
			criteria.page(),
			criteria.size(),
			totalCount,
			totalPages);
	}

	private BooleanExpression productIdContains(UUID productId) {
		return productId == null ? null : stock.id.productId.eq(productId);
	}

	private BooleanExpression hubIdContains(UUID hubId) {
		return hubId == null ? null : stock.id.hubId.eq(hubId);
	}

	private OrderSpecifier<?> getOrderSpecifier(StockSearchCriteria criteria) {
		return "updatedAt".equals(criteria.sortBy()) ? (
			criteria.isAsc() ? stock.updatedAt.asc() : stock.updatedAt.desc())
			: (criteria.isAsc() ? stock.createdAt.asc() : stock.createdAt.desc());
	}
}

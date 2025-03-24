package takeoff.logistics_service.msa.product.product.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.product.product.domain.entity.QProduct.product;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.product.product.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteria;
import takeoff.logistics_service.msa.product.product.domain.repository.search.ProductSearchCriteriaResponse;
import takeoff.logistics_service.msa.product.product.infrastructure.persistence.JpaProductRepositoryCustom;

@RequiredArgsConstructor
public class JpaProductRepositoryCustomImpl implements JpaProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PaginatedResult<ProductSearchCriteriaResponse> search(ProductSearchCriteria criteria) {
		List<ProductSearchCriteriaResponse> content = queryFactory
			.select(Projections.constructor(ProductSearchCriteriaResponse.class,
				product.id,
				product.name,
				product.companyId,
				product.createdAt,
				product.updatedAt))
			.from(product)
			.where(
				companyIdContains(criteria.companyId())
			)
			.orderBy(getOrderSpecifier(criteria))
			.offset(criteria.page())
			.limit(criteria.size())
			.fetch();

		Long totalCount = queryFactory.select(product.count())
			.from(product)
			.where(
				companyIdContains(criteria.companyId()),
				product.deletedAt.isNull()
			)
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

	private BooleanExpression companyIdContains(UUID companyId) {
		return companyId == null ? null : product.companyId.eq(companyId);
	}

	private OrderSpecifier<?> getOrderSpecifier(ProductSearchCriteria searchCriteria) {
		return "updatedAt".equals(searchCriteria.sortBy()) ? (
			searchCriteria.isAsc() ? product.updatedAt.asc() : product.updatedAt.desc())
			: (searchCriteria.isAsc() ? product.createdAt.asc() : product.createdAt.desc());
	}
}

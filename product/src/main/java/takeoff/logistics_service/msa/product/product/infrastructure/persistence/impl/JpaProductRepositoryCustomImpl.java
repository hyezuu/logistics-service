package takeoff.logistics_service.msa.product.product.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.product.product.model.entity.QProduct.product;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.product.product.infrastructure.persistence.JpaProductRepositoryCustom;
import takeoff.logistics_service.msa.product.product.model.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.product.product.model.repository.search.ProductSearchCriteria;
import takeoff.logistics_service.msa.product.product.model.repository.search.ProductSearchCriteriaResponse;

@RequiredArgsConstructor
public class JpaProductRepositoryCustomImpl implements JpaProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PaginatedResult<ProductSearchCriteriaResponse> search(ProductSearchCriteria searchCriteria) {
		List<ProductSearchCriteriaResponse> content = queryFactory
			.select(Projections.constructor(ProductSearchCriteriaResponse.class,
				product.id,
				product.name,
				product.companyId,
				product.createdAt,
				product.updatedAt))
			.from(product)
			.where(
				companyIdContains(searchCriteria.companyId())
			)
			.orderBy(getOrderSpecifier(searchCriteria))
			.offset(searchCriteria.page())
			.limit(searchCriteria.size())
			.fetch();

		Long totalCount = queryFactory.select(product.count())
			.from(product)
			.where(
				companyIdContains(searchCriteria.companyId())
			)
			.fetchOne();

		totalCount = totalCount == null ? 0 : totalCount;
		int totalPages = (int) Math.ceil((double) totalCount / searchCriteria.size());

		return new PaginatedResult<>(
			content,
			searchCriteria.page(),
			searchCriteria.size(),
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

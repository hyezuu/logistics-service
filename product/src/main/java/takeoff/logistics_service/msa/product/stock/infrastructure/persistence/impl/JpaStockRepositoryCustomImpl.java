package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.product.stock.model.entity.QStock.stock;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.product.stock.infrastructure.persistence.JpaStockRepositoryCustom;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockSearchCondition;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponseDto;

@RequiredArgsConstructor
public class JpaStockRepositoryCustomImpl implements JpaStockRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<GetStockResponseDto> search(StockSearchCondition condition, Pageable pageable) {

		List<GetStockResponseDto> content = queryFactory
			.select(Projections.constructor(GetStockResponseDto.class,
				Projections.constructor(StockIdDto.class, stock.id.productId, stock.id.hubId),
				stock.quantity,
				stock.updatedAt))
			.from(stock)
			.where(
				productIdContains(condition.productId()),
				hubIdContains(condition.hubId()),
				stock.deletedAt.isNull())
			.orderBy(getOrderSpecifier(condition))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long fetchedCount = queryFactory.select(stock.count())
			.from(stock)
			.where(
				productIdContains(condition.productId()),
				hubIdContains(condition.hubId()))
			.fetchOne();

		return new PageImpl<>(content, pageable, fetchedCount != null ? fetchedCount : 0);
	}

	private BooleanExpression productIdContains(UUID productId) {
		return productId == null ? null : stock.id.productId.eq(productId);
	}

	private BooleanExpression hubIdContains(UUID hubId) {
		return hubId == null ? null : stock.id.hubId.eq(hubId);
	}

	private OrderSpecifier<?> getOrderSpecifier(StockSearchCondition condition) {
		return "updatedAt".equals(condition.sortBy()) ? (
			condition.isAsc() ? stock.updatedAt.asc() : stock.updatedAt.desc())
			: (condition.isAsc() ? stock.createdAt.asc() : stock.createdAt.desc());
	}
}

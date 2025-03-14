package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.product.stock.infrastructure.persistence.JpaStockRepositoryCustom;

@RequiredArgsConstructor
public class JpaStockRepositoryCustomImpl implements JpaStockRepositoryCustom {

	private final JPAQueryFactory queryFactory;

}

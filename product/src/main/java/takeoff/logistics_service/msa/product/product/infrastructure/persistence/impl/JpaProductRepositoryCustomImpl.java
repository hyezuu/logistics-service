package takeoff.logistics_service.msa.product.product.infrastructure.persistence.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.product.product.infrastructure.persistence.JpaProductRepositoryCustom;

@RequiredArgsConstructor
public class JpaProductRepositoryCustomImpl implements JpaProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;
}

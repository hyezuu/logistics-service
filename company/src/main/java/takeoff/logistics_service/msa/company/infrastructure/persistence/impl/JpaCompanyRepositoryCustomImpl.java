package takeoff.logistics_service.msa.company.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.company.domain.entity.QCompany.company;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.company.domain.entity.CompanyType;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteria;
import takeoff.logistics_service.msa.company.domain.repository.search.CompanySearchCriteriaResponse;
import takeoff.logistics_service.msa.company.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.company.infrastructure.persistence.JpaCompanyRepositoryCustom;

@RequiredArgsConstructor
public class JpaCompanyRepositoryCustomImpl implements JpaCompanyRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PaginatedResult<CompanySearchCriteriaResponse> search(CompanySearchCriteria criteria) {
		List<CompanySearchCriteriaResponse> content = queryFactory
			.select(Projections.constructor(CompanySearchCriteriaResponse.class,
				company.id,
				company.companyName,
				company.companyType,
				company.hubId,
				company.address,
				company.createdAt,
				company.updatedAt))
			.from(company)
			.where(
				companyNameContains(criteria.companyName()),
				companyTypeEquals(criteria.companyType()),
				hubIdContains(criteria.hubId()),
				addressContains(criteria.address())
			)
			.orderBy(getOrderSpecifier(criteria))
			.offset(criteria.page())
			.limit(criteria.size())
			.fetch();

		Long totalCount = queryFactory.select(company.count())
			.from(company)
			.where(
				companyNameContains(criteria.companyName()),
				companyTypeEquals(criteria.companyType()),
				hubIdContains(criteria.hubId()),
				addressContains(criteria.address())
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

	private BooleanExpression companyNameContains(String companyName) {
		return companyName == null ? null : company.companyName.contains(companyName);
	}

	private BooleanExpression companyTypeEquals(CompanyType companyType) {
		return companyType == null ? null :
			company.companyType.eq(CompanyType.valueOf(companyType.name()));
	}

	private BooleanExpression hubIdContains(UUID hubId) {
		return hubId == null ? null : company.hubId.eq(hubId);
	}

	private BooleanExpression addressContains(String address) {
		return address == null ? null : company.address.contains(address);
	}

	private OrderSpecifier<?> getOrderSpecifier(CompanySearchCriteria searchCriteria) {
		return "updatedAt".equals(searchCriteria.sortBy()) ? (
			searchCriteria.isAsc() ? company.updatedAt.asc() : company.updatedAt.desc())
			: (searchCriteria.isAsc() ? company.createdAt.asc() : company.createdAt.desc());
	}
}

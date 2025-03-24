package takeoff.logistics_service.msa.slack.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.slack.domain.entity.QSlack.slack;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import takeoff.logistics_service.msa.slack.domain.entity.Slack;
import takeoff.logistics_service.msa.slack.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.slack.domain.repository.search.SlackSearchCriteria;
import takeoff.logistics_service.msa.slack.domain.repository.search.SlackSearchCriteriaResponse;
import takeoff.logistics_service.msa.slack.infrastructure.persistence.JpaSlackRepositoryCustom;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 14.
 */
@RequiredArgsConstructor
@Slf4j
public class JpaSlackRepositoryCustomImpl implements JpaSlackRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PaginatedResult<SlackSearchCriteriaResponse> searchSlack(SlackSearchCriteria slackSearchCriteria) {

        List<Slack> fetch = queryFactory.select(slack)
            .from(slack)
            .where(
                containsMessage(slackSearchCriteria.message()),
                slack.deletedAt.isNull()
            )
            .orderBy(getOrderSpecifier(slackSearchCriteria))
            .offset(slackSearchCriteria.page())
            .limit(slackSearchCriteria.size())
            .fetch();

        Long totalCount = queryFactory.select(slack.count())
            .from(slack)
            .where(
                containsMessage(slackSearchCriteria.message())
            )
            .fetchOne();

        if (totalCount == null) totalCount = 0L;

        List<SlackSearchCriteriaResponse> resultList = fetch.stream()
            .map(SlackSearchCriteriaResponse::from)
            .toList();

        int totalPages = (int) Math.ceil((double) totalCount / slackSearchCriteria.size());

        return new PaginatedResult<>(
            resultList,
            slackSearchCriteria.page(),
            slackSearchCriteria.size(),
            totalCount,
            totalPages);
    }

    private BooleanExpression containsMessage(String message) {
        return message == null ? null : slack.contents.message.containsIgnoreCase(message);
    }

    private OrderSpecifier<?> getOrderSpecifier(SlackSearchCriteria searchCriteria) {
        return "updatedAt".equals(searchCriteria.sortBy()) ? (
            searchCriteria.isAsc() ? slack.updatedAt.asc() : slack.updatedAt.desc())
            : (searchCriteria.isAsc() ? slack.createdAt.asc() : slack.createdAt.desc());
    }

}

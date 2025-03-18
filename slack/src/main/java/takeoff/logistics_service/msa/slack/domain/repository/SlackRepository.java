package takeoff.logistics_service.msa.slack.domain.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.slack.domain.entity.Slack;
import takeoff.logistics_service.msa.slack.domain.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.slack.domain.repository.search.SlackSearchCriteria;
import takeoff.logistics_service.msa.slack.domain.repository.search.SlackSearchCriteriaResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackRepository {

    Slack save(Slack slack);

    Optional<Slack> findByIdAndDeletedAtIsNull(UUID slackId);

    PaginatedResult<SlackSearchCriteriaResponse> searchSlack(SlackSearchCriteria slackSearchCriteria);
}

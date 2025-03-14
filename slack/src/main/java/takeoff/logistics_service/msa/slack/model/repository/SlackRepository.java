package takeoff.logistics_service.msa.slack.model.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackRepository {

    Slack save(Slack slack);

    Optional<Slack> findById(UUID slackId);
}

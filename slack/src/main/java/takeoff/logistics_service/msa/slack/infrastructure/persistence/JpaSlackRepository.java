package takeoff.logistics_service.msa.slack.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.model.repository.SlackRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface JpaSlackRepository extends JpaRepository<Slack, UUID>, SlackRepository,JpaSlackRepositoryCustom {
}

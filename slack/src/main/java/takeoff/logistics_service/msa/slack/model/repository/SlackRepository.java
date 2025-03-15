package takeoff.logistics_service.msa.slack.model.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostSlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SearchSlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PostSlackResponseDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackRepository {

    Slack save(Slack slack);

    Optional<Slack> findById(UUID slackId);


    Page<SearchSlackResponseDto> searchSlack(SearchSlackRequestDto searchSlackRequestDto, Pageable pageable);
}

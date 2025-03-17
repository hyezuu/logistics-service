package takeoff.logistics_service.msa.slack.infrastructure.client.ai;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.application.dto.request.PostSlackMessageRequestDto;
import takeoff.logistics_service.msa.slack.application.service.client.WebRequestClient;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Component
public interface WebClientAdapter extends WebRequestClient {

}

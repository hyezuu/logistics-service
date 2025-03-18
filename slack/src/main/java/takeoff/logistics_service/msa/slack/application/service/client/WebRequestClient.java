package takeoff.logistics_service.msa.slack.application.service.client;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.application.dto.request.PostSlackMessageRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Component
public interface WebRequestClient {
    Mono<String> sendRequestToGemini(PostSlackMessageRequestDto requestDto);
}

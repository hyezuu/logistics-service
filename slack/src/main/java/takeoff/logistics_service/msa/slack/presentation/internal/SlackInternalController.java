package takeoff.logistics_service.msa.slack.presentation.internal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostSlackMessageRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PostSlackResponse;

import takeoff.logistics_service.msa.slack.application.service.SlackService;
/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/slacks")
public class SlackInternalController {

    private final SlackService slackService;
    //생성만 내부에서 호출

    @PostMapping("/message/{userId}")
    public Mono<PostSlackResponse> saveSlackMessage(@Valid @RequestBody PostSlackMessageRequest requestDto,
        @PathVariable Long userId) {
        return slackService.saveSlackMessage(PostSlackMessageRequest
            .toApplicationDto(requestDto), userId)
            .map(PostSlackResponse::from);
    }


}

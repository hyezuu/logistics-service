package takeoff.logistics_service.msa.slack.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.slack.application.service.SlackService;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SlackResponseDto;

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

    @PostMapping("/message")
    public SlackResponseDto createSlackMessage(@RequestBody SlackRequestDto requestDto) {
        return slackService.createSlackMessage(requestDto);
    }


}

package takeoff.logistics_service.msa.slack.presentation.internal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.slack.application.service.SlackService;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostUserSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PostSlackResponse;
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
    public PostSlackResponse saveSlackMessage(@Valid @RequestBody PostSlackRequest requestDto,
        @UserInfo UserInfoDto userInfo) {
        return PostSlackResponse.from(slackService.saveSlackMessage(requestDto
            .toApplicationDto(), userInfo));
    }

    @PostMapping("/message/users/{userId}")
    public PostSlackResponse saveSlackMessageToUser(@Valid @RequestBody PostUserSlackRequest request,
        @UserInfo UserInfoDto userInfo) {
        return PostSlackResponse.from(slackService.saveSlackMessageToUser(request.toApplicationDto()
            , userInfo));
    }


}

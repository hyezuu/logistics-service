package takeoff.logistics_service.msa.slack.presentation.external;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.slack.application.service.SlackService;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/slacks")
public class SlackExternalController {

    private final SlackService slackService;

    //생성 이외 엔드포인트는 외부에서 호출
    @GetMapping("/{slackId}")
    public ResponseEntity<SlackResponseDto> findBySlackId(@PathVariable("slackId")UUID slackId) {
        return ResponseEntity.ok(slackService.findBySlackId(slackId));
    }

}

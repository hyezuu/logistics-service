package takeoff.logistics_service.msa.slack.presentation.external;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api/v1/slacks")
public class SlackExternalController {

    private final SlackService slackService;

    //생성 이외 엔드포인트는 외부에서 호출
    @GetMapping("/{slackId}")
    public ResponseEntity<SlackResponseDto> findBySlackId(@PathVariable("slackId")UUID slackId) {
        return ResponseEntity.ok(slackService.findBySlackId(slackId));
    }


    @PatchMapping("/{slackId}")
    public ResponseEntity<SlackResponseDto> updateBySlack(@PathVariable("slackId")UUID slackId,
        @RequestBody SlackRequestDto requestDto) {
        return ResponseEntity.ok(slackService.updateBySlack(slackId, requestDto));
    }
//      Auditing 설정시 추가 개발 예정
//    @DeleteMapping("/{slackId}")
//    public void deleteBySlack(@PathVariable("slackId")UUID slackId) {
//        slackService.deleteBySlack(slackId);
//    }

    @GetMapping("/search")
    public ResponseEntity<Page<SlackResponseDto>> searchSlack(SlackRequestDto slackRequestDto, Pageable pageable) {
        return ResponseEntity.ok(slackService.searchSlack(slackRequestDto, pageable));
    }

}

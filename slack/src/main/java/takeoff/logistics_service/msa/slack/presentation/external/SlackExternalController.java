package takeoff.logistics_service.msa.slack.presentation.external;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.slack.application.service.SlackService;
import takeoff.logistics_service.msa.slack.presentation.dto.PaginatedResultApi;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PatchSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SearchSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.response.GetSlackResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PatchSlackResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchSlackResponse;
/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/slacks")
@Slf4j
public class SlackExternalController {

    private final SlackService slackService;

    //생성 이외 엔드포인트는 외부에서 호출
    @GetMapping("/{slackId}")
    public ResponseEntity<GetSlackResponse> findBySlackId(@PathVariable("slackId")UUID slackId,
        @UserInfo UserInfoDto userInfo) {
        return ResponseEntity.ok(GetSlackResponse.from(slackService.findBySlackId(slackId)));
    }


    @PatchMapping("/{slackId}")
    public ResponseEntity<PatchSlackResponse> updateBySlack(@PathVariable("slackId")UUID slackId,
        @Valid @RequestBody PatchSlackRequest requestDto,
        @UserInfo UserInfoDto userInfo) {
        return ResponseEntity.ok(PatchSlackResponse
            .from(slackService.updateBySlack(
                slackId,
                PatchSlackRequest.toApplicationDto(
                    requestDto,
                    requestDto.userId()))));
    }

    @DeleteMapping("/{slackId}/")
    public ResponseEntity<Void> deleteBySlack(
        @PathVariable("slackId") UUID slackId,
        @UserInfo UserInfoDto userInfo) {
        slackService.deleteSlack(slackId, userInfo.userId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResultApi<SearchSlackResponse>> searchSlack(
        SearchSlackRequest searchSlackRequest,
        @UserInfo UserInfoDto userInfo) {
        return ResponseEntity.ok(PaginatedResultApi.from(
            slackService.searchSlack(searchSlackRequest.toApplicationDto())
        ));
    }

}

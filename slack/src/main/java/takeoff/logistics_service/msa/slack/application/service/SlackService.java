package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.application.dto.request.PostSlackMessageRequestDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PostSlackResponseDto;


import takeoff.logistics_service.msa.slack.presentation.dto.request.PatchSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SearchSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.response.GetSlackResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PatchSlackResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchSlackResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackService {

    Mono<PostSlackResponseDto> saveSlackMessage(PostSlackMessageRequestDto requestDto, Long userId);

    GetSlackResponse findBySlackId(UUID slackId);

    PatchSlackResponse updateBySlack(UUID slackId, PatchSlackRequest requestDto);

//      Auditing 설정시 추가 개발 예정
    void deleteSlack(UUID slackId);

    Page<SearchSlackResponse> searchSlack(SearchSlackRequest searchSlackRequest, Pageable pageable);

}

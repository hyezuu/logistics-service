package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PatchSlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostSlackMessageRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SearchSlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.GetSlackResponseDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PatchSlackResponseDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PostSlackResponseDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackService {

    Mono<PostSlackResponseDto> saveSlackMessage(PostSlackMessageRequestDto requestDto, Long userId);

    GetSlackResponseDto findBySlackId(UUID slackId);

    PatchSlackResponseDto updateBySlack(UUID slackId, PatchSlackRequestDto requestDto);

//      Auditing 설정시 추가 개발 예정
    void deleteSlack(UUID slackId);

    Page<SearchSlackResponseDto> searchSlack(SearchSlackRequestDto searchSlackRequestDto, Pageable pageable);

}

package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.slack.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.slack.application.dto.request.PatchSlackRequestDto;
import takeoff.logistics_service.msa.slack.application.dto.request.PostSlackMessageRequestDto;
import takeoff.logistics_service.msa.slack.application.dto.request.PostUserSlackRequestDto;
import takeoff.logistics_service.msa.slack.application.dto.request.SearchSlackRequestDto;
import takeoff.logistics_service.msa.slack.application.dto.response.GetSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PatchSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PostSlackResponseDto;
import takeoff.logistics_service.msa.slack.application.dto.response.SearchSlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackService {

    PostSlackResponseDto saveSlackMessage(PostSlackMessageRequestDto requestDto, UserInfoDto userInfo);

    GetSlackResponseDto findBySlackId(UUID slackId);

    PatchSlackResponseDto updateBySlack(UUID slackId, PatchSlackRequestDto requestDto);

    void deleteSlack(UUID slackId,Long userId);

    PaginatedResultDto<SearchSlackResponseDto> searchSlack(SearchSlackRequestDto searchSlackRequestDto);

    PostSlackResponseDto saveSlackMessageToUser(PostUserSlackRequestDto requestDto, UserInfoDto userInfo);

}

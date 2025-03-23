package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import takeoff.logistics_service.msa.slack.application.exception.SlackBusinessException;
import takeoff.logistics_service.msa.slack.application.exception.SlackErrorCode;
import takeoff.logistics_service.msa.slack.application.exception.SlackGeminiException;
import takeoff.logistics_service.msa.slack.application.service.client.WebRequestClient;
import takeoff.logistics_service.msa.slack.domain.entity.Slack;
import takeoff.logistics_service.msa.slack.domain.repository.SlackRepository;
/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SlackServiceImpl implements SlackService {

    private final SlackRepository slackRepository;
    private final WebRequestClient webRequestClient;
    private final SlackAlarmService slackAlarmService;

    @Override
    public PostSlackResponseDto saveSlackMessage(PostSlackMessageRequestDto requestDto, UserInfoDto userInfo) {
         return webRequestClient.sendRequestToGemini(requestDto)
             .onErrorMap(error -> {
                 log.error("AI 응답을 받을 수 없습니다.", error);
                 return SlackGeminiException.from(SlackErrorCode.GEMINI_ERROR);
             })
            .map(resultMessage -> {
                Slack slack = Slack.createSlack(userInfo.userId(), resultMessage);
                Slack savedSlack = slackRepository.save(slack);
                slackAlarmService.sendSlackMessageToChannel(savedSlack.getContents().getMessage(), SlackConstant.PROJECT_CHANNEL);
                return PostSlackResponseDto.from(savedSlack);
            }).block();
    }

    @Override
    public PostSlackResponseDto saveSlackMessageToUser(PostUserSlackRequestDto requestDto,
        UserInfoDto userInfo) {
        slackAlarmService.sendSlackMessageToChannel(requestDto.postContentsRequestDto().message(),
            SlackConstant.USER_CHANNEL);
        return PostSlackResponseDto.from(slackRepository.save(requestDto.toEntity(userInfo.userId())));
    }

    @Override
    @Transactional(readOnly = true)
    public GetSlackResponseDto findBySlackId(UUID slackId) {
        Slack slack = findSlack(slackId);
        return GetSlackResponseDto.from(slack);
    }

    @Override
    public PatchSlackResponseDto updateBySlack(UUID slackId, PatchSlackRequestDto requestDto) {
        Slack slack = findSlack(slackId);

        slack.getContents().modifyMessage(requestDto.patchContentsRequestDto().message());

        return PatchSlackResponseDto.from(slack);
    }

    @Override
    public PaginatedResultDto<SearchSlackResponseDto> searchSlack(SearchSlackRequestDto searchSlackRequest) {
        return PaginatedResultDto.from(
            slackRepository.searchSlack(searchSlackRequest.toSearchCriteria())
        );
    }

    @Override
    public void deleteSlack(UUID slackId, Long userId) {
        Slack slack = findSlack(slackId);
        slack.delete(userId);
    }

    private Slack findSlack(UUID slackId) {
        return slackRepository.findByIdAndDeletedAtIsNull(slackId).orElseThrow(() ->
            SlackBusinessException.from(SlackErrorCode.SLACK_NOT_FOUND));
    }
}

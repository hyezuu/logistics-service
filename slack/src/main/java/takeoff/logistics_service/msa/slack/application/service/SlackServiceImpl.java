package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.application.dto.request.PostSlackMessageRequestDto;
import takeoff.logistics_service.msa.slack.application.dto.response.PostSlackResponseDto;
import takeoff.logistics_service.msa.slack.infrastructure.client.ai.GeminiWebClient;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.model.entity.SlackConstant;
import takeoff.logistics_service.msa.slack.model.repository.SlackRepository;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PatchSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SearchSlackRequest;
import takeoff.logistics_service.msa.slack.presentation.dto.response.GetSlackResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PatchSlackResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchSlackResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SlackServiceImpl implements SlackService {

    private final SlackRepository slackRepository;
    private final GeminiWebClient geminiWebClient;
    private final SlackAlarmService slackAlarmService;

    @Override
    public Mono<PostSlackResponseDto> saveSlackMessage(PostSlackMessageRequestDto requestDto, Long userId) {
         return geminiWebClient.sendRequestToGemini(requestDto)
            .map(resultMessage -> {
                Slack slack = Slack.createSlack(userId, resultMessage);
                Slack savedSlack = slackRepository.save(slack);
                slackAlarmService.sendSlackMessage(savedSlack.getContents().getMessage(), SlackConstant.PROJECT_CHANNEL);
                return PostSlackResponseDto.from(savedSlack);
            });
    }

    @Override
    @Transactional(readOnly = true)
    public GetSlackResponse findBySlackId(UUID slackId) {
        Slack slack = findSlack(slackId);
        return GetSlackResponse.from(slack);
    }

    @Override
    public PatchSlackResponse updateBySlack(UUID slackId, PatchSlackRequest requestDto) {
        Slack slack = findSlack(slackId);

        slack.getContents().modifyMessage(requestDto.patchContentsRequest().message());

        return PatchSlackResponse.from(slack);
    }

    @Override
    public Page<SearchSlackResponse> searchSlack(SearchSlackRequest searchSlackRequest, Pageable pageable) {
        return slackRepository.searchSlack(searchSlackRequest, pageable);
    }
//      Auditing 설정시 추가 개발 예정
    @Override
    public void deleteSlack(UUID slackId) {
        findSlack(slackId);
    }

    private Slack findSlack(UUID slackId) {
        return slackRepository.findById(slackId).orElseThrow(() ->
            new IllegalArgumentException("없는 슬랙 메세지 입니다."));
    }
}

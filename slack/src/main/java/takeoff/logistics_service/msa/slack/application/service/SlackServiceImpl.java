package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.infrastructure.client.ai.GeminiWebClient;
import takeoff.logistics_service.msa.slack.model.entity.Contents;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.model.entity.SlackConstant;
import takeoff.logistics_service.msa.slack.model.repository.SlackRepository;
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
                Slack slack = Slack.builder()
                    .userId(userId)
                    .contents(Contents.create(resultMessage))
                    .build();
                slackRepository.save(slack);
                slackAlarmService.sendSlackMessage(slack.getContents().getMessage(), SlackConstant.PROJECT_CHANNEL);
                return PostSlackResponseDto.from(slack);
            });
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
    public Page<SearchSlackResponseDto> searchSlack(SearchSlackRequestDto searchSlackRequestDto, Pageable pageable) {
        return slackRepository.searchSlack(searchSlackRequestDto, pageable);
    }
//      Auditing 설정시 추가 개발 예정
    @Override
    public void deleteBySlack(UUID slackId) {
        findSlack(slackId);
    }

    private Slack findSlack(UUID slackId) {
        return slackRepository.findById(slackId).orElseThrow(() ->
            new IllegalArgumentException("없는 슬랙 메세지 입니다."));
    }
}

package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.model.repository.SlackRepository;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.ContentsResponseDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SlackServiceImpl implements SlackService {

    private final SlackRepository slackRepository;

    @Override
    public SlackResponseDto createSlackMessage(SlackRequestDto requestDto) {
        Slack savedSlack = slackRepository.save(requestDto.toEntity());
        return SlackResponseDto.from(savedSlack, ContentsResponseDto.from(savedSlack.getContents())) ;
    }

    @Override
    @Transactional
    public SlackResponseDto findBySlackId(UUID slackId) {
        Slack slack = slackRepository.findById(slackId).orElseThrow(() ->
            new IllegalArgumentException("없는 슬랙 메세지 입니다."));
        return SlackResponseDto.from(slack);
    }
}

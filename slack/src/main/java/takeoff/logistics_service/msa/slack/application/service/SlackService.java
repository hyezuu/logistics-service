package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackService {

    SlackResponseDto createSlackMessage(SlackRequestDto requestDto);

    SlackResponseDto findBySlackId(UUID slackId);
}

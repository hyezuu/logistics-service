package takeoff.logistics_service.msa.slack.application.service;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.slack.application.exception.SlackErrorCode;


@Slf4j
@Service
public class SlackAlarmService {

    private static final String SLACK_CIRCUIT_BREAKER = "slackService";
    private static final String FALL_BACK_RESPONSE = "slackRequestFail";
    private static final String SLACK_RETRY = "slackRetry";
    private final MethodsClient methodsClient;

    //Slack 객체는 재사용 가능하므로 빈으로 관리
    public SlackAlarmService(@Value("${slack.key}") String slackToken) {
        this.methodsClient = Slack.getInstance().methods(slackToken);
    }

    //retry는 yml에 설정된 값에 따라 재시도 합니다. 재시도 한 것이 모두 성공적이면 close로 진행합니다.
    @Retry(name = SLACK_RETRY, fallbackMethod = FALL_BACK_RESPONSE)
    @CircuitBreaker(name = SLACK_CIRCUIT_BREAKER, fallbackMethod = FALL_BACK_RESPONSE)
    public void sendSlackMessageToChannel(String message, String channel){
        try{

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channel)
                .text(message)
                .build();
//            서킷 브레이커 테스트용 구문
//            if (message.equals("서킷 브레이커")) {
//                throw new IllegalArgumentException("서킷 브레이커 테스트");
//            }

            methodsClient.chatPostMessage(request);

            log.info("Slack " + channel + " 에 메시지 보냄");
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
            throw BusinessException.from(SlackErrorCode.SLACK_ERROR);
        }
    }

    private void slackRequestFail(String message, String channel, Throwable t) {
        log.error("slack 서비스 장애 발생 {}", t.getMessage());
        log.error("전송하려던 메시지: {}", message);
        log.error("전송해야할 채널 {}", channel);
    }

}

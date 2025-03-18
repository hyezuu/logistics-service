package takeoff.logistics_service.msa.slack.application.service;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SlackAlarmService {

    @Value(value = "${slack.key}")
    String slackToken;

    public void sendSlackMessage(String message, String channel){
        try{
            MethodsClient methods = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(SlackConstant.PROJECT_CHANNEL)
                .text(message)
                .build();

            methods.chatPostMessage(request);

            log.info("Slack " + channel + " 에 메시지 보냄");
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }

}

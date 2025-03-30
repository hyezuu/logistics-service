package takeoff.logistics_service.msa.hub.hubroute.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.service.kafka.HubRouteEventProducer;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HubRouteEventKafkaProducer implements HubRouteEventProducer {

    private final KafkaTemplate<String, PostHubRouteRequestDto> kafkaTemplate;
    private final String hubRouteTopicName;

    @Override
    public void sendToHub(PostHubRouteRequestDto event) {
        kafkaTemplate.send(hubRouteTopicName, event);
        log.info(hubRouteTopicName , ": 이벤트 발행");
    }
}

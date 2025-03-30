package takeoff.logistics_service.msa.hub.hub.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.hub.hub.application.dto.kafka.KafkaFromToHubDto;
import takeoff.logistics_service.msa.hub.hub.application.service.kafka.HubEventProducer;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HubRouteEventKafkaProducer implements HubEventProducer {

    private final KafkaTemplate<String, KafkaFromToHubDto> kafkaTemplate;
    private final String hubTopicName;

    @Override
    public void sendToHubRoute(KafkaFromToHubDto event) {
        kafkaTemplate.send(hubTopicName, event);
        log.info(hubTopicName , ": 이벤트 발행");
    }
}

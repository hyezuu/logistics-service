package takeoff.logistics_service.msa.hub.hub.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.hub.hub.application.dto.HubIdsDto;
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HubKafkaListener {

    private final HubService hubService;

    @KafkaListener(
        topics = "hubRoute-events",
        containerFactory = "hubIdsDtoKafkaListenerContainerFactory"
    )
    public void handleHubListResponse(HubIdsDto event) {
        log.info("허브 리스트 응답 수신: {}", event);
        hubService.findByToHubIdAndFromHubIdToKafka(event);
    }


}

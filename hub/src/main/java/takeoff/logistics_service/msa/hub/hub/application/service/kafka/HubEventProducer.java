package takeoff.logistics_service.msa.hub.hub.application.service.kafka;

import takeoff.logistics_service.msa.hub.hub.application.dto.kafka.KafkaFromToHubDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
public interface HubEventProducer {
    void sendToHubRoute(KafkaFromToHubDto event);

}

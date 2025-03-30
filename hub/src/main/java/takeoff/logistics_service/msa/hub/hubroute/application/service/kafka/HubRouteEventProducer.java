package takeoff.logistics_service.msa.hub.hubroute.application.service.kafka;

import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PostHubRouteRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
public interface HubRouteEventProducer {
    void sendToHub(PostHubRouteRequestDto event);
}


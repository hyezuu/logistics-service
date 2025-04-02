package takeoff.logistics_service.msa.product.product.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.product.product.application.event.ProductCreatedEvent;
import takeoff.logistics_service.msa.product.product.application.event.ProductEvent;
import takeoff.logistics_service.msa.product.product.application.event.ProductEventPublisher;
import takeoff.logistics_service.msa.product.product.application.event.ProductUpdatedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProductProducer implements ProductEventPublisher {

	private final KafkaTemplate<String, ProductEvent> productKafkaTemplate;
	private final String productTopicName;

	@Override
	public void publish(ProductCreatedEvent productEvent) {
		productKafkaTemplate.send(productTopicName, productEvent);
		log.info("Published product event {}", productEvent.eventType());
	}
}

package takeoff.logistics_service.msa.product.product.application.event;

public interface ProductEventPublisher {

	void publish(ProductCreatedEvent createdEvent);
}

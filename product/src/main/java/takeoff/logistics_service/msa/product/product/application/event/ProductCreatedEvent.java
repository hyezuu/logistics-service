package takeoff.logistics_service.msa.product.product.application.event;

import static takeoff.logistics_service.msa.product.product.application.event.EventType.CREATED;

import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;

public record ProductCreatedEvent(
	EventType eventType, PostStockRequestDto payload) implements ProductEvent {

	public static ProductCreatedEvent from(PostStockRequestDto payload) {
		return new ProductCreatedEvent(CREATED, payload);
	}
}

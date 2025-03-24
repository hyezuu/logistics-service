package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response;

public record GetHubDeliveryManagerResponseDto(
    Long userId,
    Integer deliverySequence
) {
}

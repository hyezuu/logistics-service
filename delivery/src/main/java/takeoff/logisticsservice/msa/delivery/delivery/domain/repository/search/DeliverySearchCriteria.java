package takeoff.logisticsservice.msa.delivery.delivery.domain.repository.search;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliverySearchCriteria(
    UUID orderId,
    UUID hubId,
    Long customerId,
    Long deliveryManagerId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean isAsc,
    String sortBy,
    Integer page,
    Integer size
) {
}

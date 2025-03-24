package takeoff.logistics_service.msa.order.domain.repository.search;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderSearchCriteria(
    Long customerId,
    UUID supplierId,
    List<UUID> deliveryIds,
    UUID hubId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean isAsc,
    String sortBy,
    Integer page,
    Integer size
) {
}

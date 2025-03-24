package takeoff.logistics_service.msa.order.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteria;

public record SearchOrderRequestDto(
    UUID supplierId,
    UUID hubId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean isAsc,
    String sortBy,
    Integer page,
    Integer size
) {

  public OrderSearchCriteria toSearchCriteria() {
    return new OrderSearchCriteria(
        null,
        supplierId(),
        null,
        hubId(),
        startDate(),
        endDate(),
        isAsc(),
        sortBy(),
        page(),
        size()
    );
  }

  public OrderSearchCriteria toSearchCriteria(Long userId) {
    return new OrderSearchCriteria(
        userId,
        supplierId(),
        null,
        hubId(),
        startDate(),
        endDate(),
        isAsc(),
        sortBy(),
        page(),
        size()
    );
  }

  public OrderSearchCriteria toSearchCriteria(List<UUID> deliveryIds) {
    return new OrderSearchCriteria(
        null,
        supplierId(),
        deliveryIds,
        hubId(),
        startDate(),
        endDate(),
        isAsc(),
        sortBy(),
        page(),
        size()
    );
  }

}

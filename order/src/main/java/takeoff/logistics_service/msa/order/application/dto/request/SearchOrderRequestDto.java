package takeoff.logistics_service.msa.order.application.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;
import takeoff.logistics_service.msa.order.domain.repository.search.OrderSearchCriteria;

public record SearchOrderRequestDto(
    Long customerId,
    UUID supplierId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean isAsc,
    String sortBy,
    Integer page,
    Integer size
) {

  public OrderSearchCriteria toSearchCriteria() {
    return new OrderSearchCriteria(
        customerId(),
        supplierId(),
        startDate(),
        endDate(),
        isAsc(),
        sortBy(),
        page(),
        size()
    );
  }
}

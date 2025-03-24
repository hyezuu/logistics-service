package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import takeoff.logistics_service.msa.order.application.dto.request.SearchOrderRequestDto;

public record SearchOrderRequest(
    UUID supplierId,
    UUID hubId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean isAsc,
    String sortBy,
    Integer page,
    Integer size
) {

  public SearchOrderRequest {
    isAsc = isAsc != null ? isAsc : false;
    sortBy = sortBy != null ? sortBy : "createdAt";
    size = (size != null && Set.of(10, 30, 50).contains(size)) ? size : 10;
    page = page != null ? page : 0;
  }

  public SearchOrderRequestDto toApplicationDto() {
    return new SearchOrderRequestDto(
        supplierId,
        hubId,
        startDate,
        endDate,
        isAsc,
        sortBy,
        page,
        size
    );
  }
}

package takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.request.SearchDeliveryRequestDto;

public record SearchDeliveryRequest(
    UUID orderId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean isAsc,
    String sortBy,
    Integer page,
    Integer size
) {

  public SearchDeliveryRequest {
    isAsc = isAsc != null ? isAsc : false;
    sortBy = sortBy != null ? sortBy : "createdAt";
    size = (size != null && Set.of(10, 30, 50).contains(size)) ? size : 10;
    page = page != null ? page : 0;
  }

  public SearchDeliveryRequestDto toApplicationDto() {
    return new SearchDeliveryRequestDto(
        orderId,
        startDate,
        endDate,
        isAsc,
        sortBy,
        page,
        size
    );
  }
}

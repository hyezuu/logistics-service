package takeoff.logistics_service.msa.order.application.client.dto.request;

import java.util.List;

public record PrePareStockRequestDto(List<StockItemRequestDto> stocks) {

}

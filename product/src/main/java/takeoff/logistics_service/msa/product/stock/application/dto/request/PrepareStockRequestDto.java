package takeoff.logistics_service.msa.product.stock.application.dto.request;

import java.util.List;

public record PrepareStockRequestDto(List<StockItemRequestDto> stocks) {
}

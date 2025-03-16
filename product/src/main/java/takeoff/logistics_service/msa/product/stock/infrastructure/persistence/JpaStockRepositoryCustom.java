package takeoff.logistics_service.msa.product.stock.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockSearchCondition;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponseDto;

public interface JpaStockRepositoryCustom {

	Page<GetStockResponseDto> search(StockSearchCondition condition, Pageable pageable);
}

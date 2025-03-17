package takeoff.logistics_service.msa.product.stock.application.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.product.stock.application.dto.StockSearchCondition;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.DecreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.IncreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PrepareStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.DecreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.IncreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.PostStockResponseDto;

public interface StockService {

	PostStockResponseDto saveStock(PostStockRequestDto requestDto);

	IncreaseStockResponseDto increaseStock(IncreaseStockRequestDto requestDto);

	DecreaseStockResponseDto decreaseStock(DecreaseStockRequestDto requestDto);

	void delete(StockIdDto stockIdDto);

	GetStockResponseDto findStock(StockIdDto stockIdDto);

	void prepareStock(PrepareStockRequestDto requestDto);

	void abortStock(AbortStockRequestDto requestDto);

	Page<GetStockResponseDto> searchStock(StockSearchCondition condition, Pageable pageable);

	void deleteAllByProductId(UUID productId);

	void deleteAllByHubId(UUID hubId);
}

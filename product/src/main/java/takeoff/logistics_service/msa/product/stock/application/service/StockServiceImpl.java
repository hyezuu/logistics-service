package takeoff.logistics_service.msa.product.stock.application.service;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.product.stock.application.exception.StockBusinessException;
import takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.model.entity.StockId;
import takeoff.logistics_service.msa.product.stock.model.repository.StockRepository;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.DecreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.IncreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PrepareStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockItemRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.StockSearchCondition;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.DecreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.IncreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.PostStockResponseDto;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

	private final StockRepository stockRepository;

	@Override
	public PostStockResponseDto saveStock(PostStockRequestDto requestDto) {
		return PostStockResponseDto.from(stockRepository.save(requestDto.toEntity()));
	}

	@Override
	@Transactional(readOnly = true)
	public GetStockResponseDto findStock(StockIdDto stockIdDto) {
		return GetStockResponseDto.from(getStock(stockIdDto.toVo()));
	}

	private Stock getStock(StockId stockId) {
		return stockRepository.findByIdAndDeletedAtIsNull(stockId)
			.orElseThrow(() -> StockBusinessException.from(StockErrorCode.STOCK_NOT_FOUND));
	}

	@Override
	@Transactional
	public void delete(StockIdDto stockIdDto) {
		getStock(stockIdDto.toVo()).delete(0L);
	}

	@Override
	@Transactional
	public void prepareStock(PrepareStockRequestDto requestDto) {
		getSortedStocks(requestDto.stocks())
			.forEach(stockItem ->
				getStockWithLock(stockItem.stockId()).decreaseStock(stockItem.quantity()));
	}

	private List<StockItemRequestDto> getSortedStocks(List<StockItemRequestDto> stocks) {
		return stocks.stream()
			.sorted(Comparator
				.comparing((StockItemRequestDto item) -> item.stockId().productId())
				.thenComparing(item -> item.stockId().hubId()))
			.toList();
	}

	private Stock getStockWithLock(StockIdDto stockIdDto) {
		return stockRepository
			.findByIdWithLock(stockIdDto.toVo())
			.orElseThrow(() -> StockBusinessException.from(StockErrorCode.STOCK_NOT_FOUND));
	}

	@Override
	@Transactional
	public void abortStock(AbortStockRequestDto requestDto) {
		getSortedStocks(requestDto.stocks())
			.forEach(stockItem ->
				getStockWithLock(stockItem.stockId()).increaseStock(stockItem.quantity()));
	}

	@Override
	@Transactional
	public IncreaseStockResponseDto increaseStock(IncreaseStockRequestDto requestDto) {
		Stock stock = getStockWithLock(requestDto.stockId());
		stock.increaseStock(requestDto.quantity());
		return IncreaseStockResponseDto.from(stock);
	}

	@Override
	@Transactional
	public DecreaseStockResponseDto decreaseStock(DecreaseStockRequestDto requestDto) {
		Stock stock = getStockWithLock(requestDto.stockId());
		stock.decreaseStock(requestDto.quantity());
		return DecreaseStockResponseDto.from(stock);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<GetStockResponseDto> searchStock(
		StockSearchCondition condition, Pageable pageable) {
		return stockRepository.search(condition, pageable);
	}
}

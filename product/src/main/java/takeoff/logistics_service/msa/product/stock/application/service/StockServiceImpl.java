package takeoff.logistics_service.msa.product.stock.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.product.stock.model.entity.Stock;
import takeoff.logistics_service.msa.product.stock.model.entity.StockId;
import takeoff.logistics_service.msa.product.stock.model.repository.StockRepository;
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
		return stockRepository.findById(stockId).orElseThrow();
	}

	@Override
	@Transactional
	public void delete(StockIdDto stockIdDto) {
		getStock(stockIdDto.toVo()).delete("");
	}

	@Override
	@Transactional
	public void prepareStock(PrepareStockRequestDto requestDto) {
		requestDto.stocks().forEach(stockItem -> {
			getStockWithLock(stockItem.stockId())
				.decreaseStock(stockItem.quantity());
		});
	}

	private Stock getStockWithLock(StockIdDto stockIdDto) {
		return stockRepository
			.findByIdWithLock(stockIdDto.toVo())
			.orElseThrow();
	}

	@Override
	@Transactional
	public void abortStock(AbortStockRequestDto requestDto) {
		requestDto.stocks().forEach(stockItem -> {
			getStockWithLock(stockItem.stockId())
				.increaseStock(stockItem.quantity());
		});
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
}

package takeoff.logistics_service.msa.product.stock.application.service;

import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.ACCESS_DENIED;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.DUPLICATE_STOCK_ID;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.STOCK_LOCK_TIMEOUT;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.STOCK_NOT_FOUND;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.product.stock.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.DecreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.IncreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PrepareStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.SearchStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockIdRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockItemRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.DecreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.IncreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.exception.StockBusinessException;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;
import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;
import takeoff.logistics_service.msa.product.stock.domain.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

	private final UserClient userClient;
	private final StockRepository stockRepository;

	@Override
	public PostStockResponseDto saveStock(PostStockRequestDto requestDto, UserInfoDto userInfo) {
		validateStockNotExists(requestDto);
		validateAccess(requestDto.stockId().hubId(), userInfo);
		return PostStockResponseDto.from(
			stockRepository.save(Stock.create(requestDto.toCommand())));
	}

	private void validateStockNotExists(PostStockRequestDto requestDto) {
		if (stockRepository.existsById(StockId.create(requestDto.stockId().toCommand()))) {
			throw StockBusinessException.from(DUPLICATE_STOCK_ID);
		}
	}

	private void validateAccess(UUID resourceId, UserInfoDto userInfo) {
		if (userInfo.isHubManager() && !getHubId(userInfo).equals(resourceId)){
			throw StockBusinessException.from(ACCESS_DENIED);
		}
	}

	private UUID getHubId(UserInfoDto userInfo) {
		return userClient.findByHubManagerId(userInfo.userId()).hubId();
	}

	@Override
	@Transactional(readOnly = true)
	public GetStockResponseDto findStock(StockIdRequestDto requestDto) {
		return GetStockResponseDto.from(getStock(StockId.create(requestDto.toCommand())));
	}

	private Stock getStock(StockId stockId) {
		return stockRepository.findByIdAndDeletedAtIsNull(stockId)
			.orElseThrow(() -> StockBusinessException.from(STOCK_NOT_FOUND));
	}

	@Override
	@Transactional
	public void delete(StockIdRequestDto requestDto, UserInfoDto userInfo) {
		validateAccess(requestDto.hubId(), userInfo);
		getStock(StockId.create(requestDto.toCommand())).delete(userInfo.userId());
	}

	@Override
	@Transactional
	public IncreaseStockResponseDto increaseStock(
		IncreaseStockRequestDto requestDto, UserInfoDto userInfoDto) {

		validateAccess(requestDto.stockId().hubId(), userInfoDto);
		try {
			return IncreaseStockResponseDto.from(getStockWithLock(requestDto.stockId())
				.increaseStock(requestDto.quantity()));
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(STOCK_LOCK_TIMEOUT);
		}
	}

	@Override
	@Transactional
	public DecreaseStockResponseDto decreaseStock(
		DecreaseStockRequestDto requestDto, UserInfoDto userInfoDto) {

		validateAccess(requestDto.stockId().hubId(), userInfoDto);
		try {
			return DecreaseStockResponseDto.from(getStockWithLock(requestDto.stockId())
				.decreaseStock(requestDto.quantity()));
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(STOCK_LOCK_TIMEOUT);
		}
	}

	private Stock getStockWithLock(StockIdRequestDto requestDto) {
		return stockRepository
			.findByIdWithLock(StockId.create(requestDto.toCommand()))
			.orElseThrow(() -> StockBusinessException.from(STOCK_NOT_FOUND));
	}

	@Override
	@Transactional
	public void prepareStock(PrepareStockRequestDto requestDto) {
		try {
			getSortedStocks(requestDto.stocks())
				.forEach(stockItem ->
					getStockWithLock(stockItem.stockId()).decreaseStock(stockItem.quantity()));
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(STOCK_LOCK_TIMEOUT);
		}
	}

	private List<StockItemRequestDto> getSortedStocks(List<StockItemRequestDto> stocks) {
		return stocks.stream()
			.sorted(Comparator
				.comparing((StockItemRequestDto item) -> item.stockId().productId())
				.thenComparing(item -> item.stockId().hubId()))
			.toList();
	}

	@Override
	@Transactional
	public void abortStock(AbortStockRequestDto requestDto) {
		try {
			getSortedStocks(requestDto.stocks())
				.forEach(stockItem ->
					getStockWithLock(stockItem.stockId()).increaseStock(stockItem.quantity()));
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(STOCK_LOCK_TIMEOUT);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedResultDto<GetStockResponseDto> searchStock(
		SearchStockRequestDto requestDto) {
		return PaginatedResultDto.from(stockRepository.search(requestDto.toSearchCriteria()));
	}

	@Override
	@Transactional
	public void deleteAllByProductId(UUID productId, UserInfoDto userInfo) {
		stockRepository.findAllById_ProductIdAndDeletedAtIsNull(productId)
			.forEach(stock -> stock.delete(userInfo.userId()));
	}

	@Override
	@Transactional
	public void deleteAllByHubId(UUID hubId, UserInfoDto userInfo) {
		validateAccess(hubId, userInfo);
		stockRepository.findAllById_HubIdAndDeletedAtIsNull(hubId)
			.forEach(stock -> stock.delete(userInfo.userId()));
	}

	@Override
	@Transactional(readOnly = true)
	public GetStockResponseDto findStockWithProductId(UUID productId) {
		return stockRepository
			.findAllById_ProductIdAndDeletedAtIsNullOrderByQuantityDesc(productId).stream()
			.findFirst()
			.map(GetStockResponseDto::from)
			.orElseThrow(() -> StockBusinessException.from(STOCK_NOT_FOUND));
	}
}

package takeoff.logistics_service.msa.product.stock.application.service;

import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.ACCESS_DENIED;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.DUPLICATE_STOCK_ID;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.STOCK_LOCK_TIMEOUT;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.STOCK_NOT_FOUND;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
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
		validateAccessToHub(requestDto.stockId().hubId(), userInfo);
		return PostStockResponseDto.from(
			stockRepository.save(Stock.create(requestDto.toCommand())));
	}

	private void validateStockNotExists(PostStockRequestDto requestDto) {
		if (stockRepository.existsById(StockId.create(requestDto.stockId().toCommand()))) {
			throw StockBusinessException.from(DUPLICATE_STOCK_ID);
		}
	}

	private void validateAccessToHub(UUID resourceId, UserInfoDto userInfo) {
		boolean isHubManager = userInfo.role() == UserRole.HUB_MANAGER;
		if (isHubManager && !getHubId(userInfo).equals(resourceId)) {
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
		validateAccessToHub(requestDto.hubId(), userInfo);
		getStock(StockId.create(requestDto.toCommand())).delete(userInfo.userId());
	}

	@Override
	@Transactional
	public IncreaseStockResponseDto increaseStock(
		IncreaseStockRequestDto requestDto, UserInfoDto userInfo) {
		validateAccessToHub(requestDto.stockId().hubId(), userInfo);
		return withPessimisticLock(() ->
			IncreaseStockResponseDto.from(getStockWithLock(requestDto.stockId())
				.increaseStock(requestDto.quantity()))
		);
	}

	private <T> T withPessimisticLock(Supplier<T> supplier) {
		try {
			return supplier.get();
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(STOCK_LOCK_TIMEOUT);
		}
	}

	@Override
	@Transactional
	public DecreaseStockResponseDto decreaseStock(
		DecreaseStockRequestDto requestDto, UserInfoDto userInfoDto) {
		validateAccessToHub(requestDto.stockId().hubId(), userInfoDto);
		return withPessimisticLock(() ->
			DecreaseStockResponseDto.from(getStockWithLock(requestDto.stockId())
				.decreaseStock(requestDto.quantity())));
	}

	private Stock getStockWithLock(StockIdRequestDto requestDto) {
		return stockRepository
			.findByIdWithLock(StockId.create(requestDto.toCommand()))
			.orElseThrow(() -> StockBusinessException.from(STOCK_NOT_FOUND));
	}

	@Override
	@Transactional
	public void prepareStock(PrepareStockRequestDto requestDto) {
		withPessimisticLock(() -> getSortedStocks(requestDto.stocks())
			.forEach(stockItem ->
				getStockWithLock(stockItem.stockId()).decreaseStock(stockItem.quantity())));
	}

	private void withPessimisticLock(Runnable runnable) {
		try {
			runnable.run();
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(STOCK_LOCK_TIMEOUT);
		}
	}

	private List<StockItemRequestDto> getSortedStocks(List<StockItemRequestDto> stocks) {
		return stocks.stream()
			.sorted(Comparator
				.comparing((StockItemRequestDto item) -> item.stockId().productId())
				.thenComparing(item -> item.stockId().hubId())).toList();
	}

	@Override
	@Transactional
	public void abortStock(AbortStockRequestDto requestDto) {
		withPessimisticLock(() -> getSortedStocks(requestDto.stocks())
			.forEach(stockItem ->
				getStockWithLock(stockItem.stockId()).increaseStock(stockItem.quantity())));
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
		validateAccessToHub(hubId, userInfo);
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

package takeoff.logistics_service.msa.product.stock.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.product.stock.domain.command.CreateStock;
import takeoff.logistics_service.msa.product.stock.domain.command.CreateStockId;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;
import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;
import takeoff.logistics_service.msa.product.stock.domain.repository.StockRepository;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PrepareStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockIdRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockItemRequest;

@SpringBootTest
@ActiveProfiles("test")
class StockServiceConcurrencyTest {

	@Autowired
	private StockService stockService;

	@Autowired
	private StockRepository stockRepository;

	private UUID productId1;
	private UUID productId2;
	private UUID hubId;

	@BeforeEach
	void setUp() {
		productId1 = UUID.randomUUID();
		productId2 = UUID.randomUUID();
		hubId = UUID.randomUUID();

		StockId stockId1 = StockId.create(new CreateStockId(productId1, hubId));
		Stock stock1 = Stock.create(new CreateStock(stockId1, 100));

		StockId stockId2 = StockId.create(new CreateStockId(productId2, hubId));
		Stock stock2 = Stock.create(new CreateStock(stockId2, 100));

		stockRepository.save(stock1);
		stockRepository.save(stock2);
	}

	@Test
	void 기본_동시성_테스트() throws InterruptedException {
		int threadCount = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// 각 스레드에서 사용할 요청 객체 생성
		PrepareStockRequest requestDto = createRequestDto();

		// 여러 스레드에서 동시에 재고 감소 요청
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					stockService.prepareStock(requestDto.toApplicationDto());
				} catch (BusinessException e) {
					System.out.println("Exception c: " + e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// 결과 검증
		Stock stock1 = stockRepository
			.findByIdAndDeletedAtIsNull(
				StockId.create(new CreateStockId(productId1, hubId)))
			.orElseThrow();
		Stock stock2 = stockRepository
			.findByIdAndDeletedAtIsNull(
				StockId.create(new CreateStockId(productId2, hubId)))
			.orElseThrow();

		// 각 스레드에서 5개씩 감소, 10개 스레드 = 50개 감소
		assertThat(stock1.getQuantity()).isEqualTo(100 - (5 * threadCount));
		assertThat(stock2.getQuantity()).isEqualTo(100 - (3 * threadCount));
	}

	@Test
	void 정렬되지_않은_경우에도_데드락이_발생하지_않는다() throws InterruptedException {
		int threadCount = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// 스레드 절반은 정방향, 절반은 역방향 순서로 요청하여 데드락 가능성 테스트
		for (int i = 0; i < threadCount; i++) {
			final boolean reverseOrder = i % 2 == 0;
			executorService.submit(() -> {
				try {
					PrepareStockRequest requestDto = reverseOrder ?
						createReverseOrderRequestDto() : createRequestDto();
					stockService.prepareStock(requestDto.toApplicationDto());
				} catch (Exception e) {
					System.out.println("Exception occurred: " + e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// 결과 검증
		Stock stock1 = stockRepository
			.findByIdAndDeletedAtIsNull(
				StockId.create(new CreateStockId(productId1, hubId)))
			.orElseThrow();
		Stock stock2 = stockRepository
			.findByIdAndDeletedAtIsNull(
				StockId.create(new CreateStockId(productId2, hubId))).orElseThrow();

		// 정확한 감소량 확인 (예상: 스레드 개수 * 각 요청당 감소량)
		int expectedDecrease1 = 5 * threadCount;
		int expectedDecrease2 = 3 * threadCount;

		assertThat(stock1.getQuantity()).isEqualTo(100 - expectedDecrease1);
		assertThat(stock2.getQuantity()).isEqualTo(100 - expectedDecrease2);
	}

	// 정방향 순서 요청 (productId1, productId2 순)
	private PrepareStockRequest createRequestDto() {
		List<StockItemRequest> items = new ArrayList<>();

		items.add(new StockItemRequest(new StockIdRequest(productId1, hubId), 5));
		items.add(new StockItemRequest(new StockIdRequest(productId2, hubId), 3));

		return new PrepareStockRequest(items);
	}

	// 역방향 순서 요청 (productId2, productId1 순)
	private PrepareStockRequest createReverseOrderRequestDto() {
		List<StockItemRequest> items = new ArrayList<>();

		items.add(new StockItemRequest(new StockIdRequest(productId2, hubId), 3));
		items.add(new StockItemRequest(new StockIdRequest(productId1, hubId), 5));
		return new PrepareStockRequest(items);
	}
}
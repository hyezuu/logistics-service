package takeoff.logistics_service.msa.product.stock.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.product.stock.application.service.StockService;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PrepareStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.PostStockResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/stock")
public class StockInternalController {

	private final StockService stockService;

	// 권한검사
	@PostMapping
	public PostStockResponseDto saveStock(@RequestBody PostStockRequestDto requestDto) {
		return stockService.saveStock(requestDto);
	}

	@PostMapping("/prepare")
	public void prepareStock(@RequestBody PrepareStockRequestDto requestDto) {
		stockService.prepareStock(requestDto);
	}

	@PostMapping
	public void abortStock(@RequestBody AbortStockRequestDto requestDto) {
		stockService.abortStock(requestDto);
	}
}

package takeoff.logistics_service.msa.product.stock.presentation.internal;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public PostStockResponseDto saveStock(@RequestBody @Valid PostStockRequestDto requestDto) {
		return stockService.saveStock(requestDto);
	}

	@PostMapping("/prepare")
	public void prepareStock(@RequestBody PrepareStockRequestDto requestDto) {
		stockService.prepareStock(requestDto);
	}

	@PostMapping("/abort")
	public void abortStock(@RequestBody AbortStockRequestDto requestDto) {
		stockService.abortStock(requestDto);
	}

	@DeleteMapping("/all-by-product")
	public ResponseEntity<Void> deleteAllByProductId(@RequestParam UUID productId) {

		stockService.deleteAllByProductId(productId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/all-by-hub")
	public ResponseEntity<Void> deleteAllByHubId(@RequestParam UUID hubId) {

		stockService.deleteAllByHubId(hubId);
		return ResponseEntity.noContent().build();
	}
}

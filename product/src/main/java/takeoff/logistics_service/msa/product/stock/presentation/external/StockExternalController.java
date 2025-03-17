package takeoff.logistics_service.msa.product.stock.presentation.external;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.product.stock.application.dto.DeleteStockCondition;
import takeoff.logistics_service.msa.product.stock.application.service.StockService;
import takeoff.logistics_service.msa.product.stock.presentation.dto.StockIdDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.DecreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.IncreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.StockSearchCondition;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.DecreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.IncreaseStockResponseDto;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockExternalController {

	private final StockService stockService;

	@GetMapping
	public ResponseEntity<GetStockResponseDto> findStock(
		@Valid @RequestBody StockIdDto stockIdDto) {

		return ResponseEntity.ok(stockService.findStock(stockIdDto));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<GetStockResponseDto>> searchStock(
		@ModelAttribute StockSearchCondition condition, Pageable pageable) {

		return ResponseEntity.ok(stockService.searchStock(condition, pageable));
	}

	@PatchMapping("/increase")
	public ResponseEntity<IncreaseStockResponseDto> increaseStock(
		@Valid @RequestBody IncreaseStockRequestDto requestDto) {

		return ResponseEntity.ok(stockService.increaseStock(requestDto));
	}

	@PatchMapping("/decrease")
	public ResponseEntity<DecreaseStockResponseDto> decreaseStock(
		@Valid @RequestBody DecreaseStockRequestDto requestDto) {

		return ResponseEntity.ok(stockService.decreaseStock(requestDto));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteStock(@Valid @RequestBody StockIdDto stockIdDto) {

		stockService.delete(stockIdDto);
		return ResponseEntity.noContent().build();
	}
}

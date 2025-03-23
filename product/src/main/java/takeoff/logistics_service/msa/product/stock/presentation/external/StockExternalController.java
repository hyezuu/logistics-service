package takeoff.logistics_service.msa.product.stock.presentation.external;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.product.stock.application.service.StockService;
import takeoff.logistics_service.msa.product.stock.presentation.dto.PaginatedResultApi;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.DecreaseStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.IncreaseStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.SearchStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.StockIdRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.DecreaseStockResponse;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponse;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.IncreaseStockResponse;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockExternalController {

	private final StockService stockService;

	@GetMapping
	public ResponseEntity<GetStockResponse> findStock(
		@Valid @RequestBody StockIdRequest requestDto) {

		return ResponseEntity.ok(GetStockResponse
			.from(stockService.findStock(requestDto.toApplicationDto())));
	}

	@GetMapping("/search")
	public ResponseEntity<PaginatedResultApi<GetStockResponse>> searchStock(
		@ModelAttribute SearchStockRequest requestDto) {

		return ResponseEntity.ok(PaginatedResultApi
			.from(stockService.searchStock(requestDto.toApplicationDto())));
	}

	@PatchMapping("/increase")
	@RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER, UserRole.COMPANY_MANAGER})
	public ResponseEntity<IncreaseStockResponse> increaseStock(
		@Valid @RequestBody IncreaseStockRequest requestDto, @UserInfo UserInfoDto userInfo) {

		return ResponseEntity.ok(IncreaseStockResponse
			.from(stockService.increaseStock(requestDto.toApplicationDto(), userInfo)));
	}

	@PatchMapping("/decrease")
	@RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER, UserRole.COMPANY_MANAGER})
	public ResponseEntity<DecreaseStockResponse> decreaseStock(
		@Valid @RequestBody DecreaseStockRequest requestDto, @UserInfo UserInfoDto userInfo) {

		return ResponseEntity.ok(DecreaseStockResponse
			.from(stockService.decreaseStock(requestDto.toApplicationDto(), userInfo)));
	}

	@DeleteMapping
	@RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
	public ResponseEntity<Void> deleteStock(
		@Valid @RequestBody StockIdRequest requestDto, @UserInfo UserInfoDto userInfo) {

		stockService.delete(requestDto.toApplicationDto(), userInfo);
		return ResponseEntity.noContent().build();
	}
}

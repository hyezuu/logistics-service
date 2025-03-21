package takeoff.logistics_service.msa.product.stock.presentation.internal;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.common.annotation.RoleCheck;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.product.stock.application.service.StockService;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.AbortStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PostStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.request.PrepareStockRequest;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.GetStockResponse;
import takeoff.logistics_service.msa.product.stock.presentation.dto.response.PostStockResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/stock")
public class StockInternalController {

	private final StockService stockService;

	@PostMapping
	@RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER, UserRole.COMPANY_MANAGER})
	public PostStockResponse saveStock(
		@RequestBody @Valid PostStockRequest requestDto, @UserInfo UserInfoDto userInfo) {

		return PostStockResponse
			.from(stockService.saveStock(requestDto.toApplicationDto(), userInfo));
	}

	@GetMapping
	public GetStockResponse findStockWithProductId(@RequestParam UUID productId) {

		return GetStockResponse.from(stockService.findStockWithProductId(productId));
	}

	@PostMapping("/prepare")
	public void prepareStock(@RequestBody PrepareStockRequest requestDto) {
		stockService.prepareStock(requestDto.toApplicationDto());
	}

	@PostMapping("/abort")
	public void abortStock(@RequestBody AbortStockRequest requestDto) {
		stockService.abortStock(requestDto.toApplicationDto());
	}

	@DeleteMapping("/all-by-product")
	@RoleCheck(roles = {UserRole.MASTER_ADMIN})
	public void deleteAllByProductId(@RequestParam UUID productId, @UserInfo UserInfoDto userInfo) {

		stockService.deleteAllByProductId(productId, userInfo);
	}

	@DeleteMapping("/all-by-hub")
	@RoleCheck(roles = {UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER})
	public void deleteAllByHubId(@RequestParam UUID hubId, @UserInfo UserInfoDto userInfo) {

		stockService.deleteAllByHubId(hubId, userInfo);
	}
}

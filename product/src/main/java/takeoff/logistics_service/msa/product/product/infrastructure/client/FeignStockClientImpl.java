package takeoff.logistics_service.msa.product.product.infrastructure.client;

import feign.FeignException.FeignClientException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
import takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode;
import takeoff.logistics_service.msa.product.product.application.service.StockClient;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request.PostStockRequest;

@Component
@RequiredArgsConstructor
public class FeignStockClientImpl implements StockClient {

	private final FeignStockClient feignStockClient;

	@Override
	public PostStockResponseDto saveStock(PostStockRequestDto requestDto) {
		try {
			return feignStockClient.saveStock(PostStockRequest.from(requestDto)).toApplicationDto();
		} catch (FeignClientException e) {
			throw handleFeignException(e);
		}
	}

	@Override
	public void deleteStock(UUID productId) {
		try {
			feignStockClient.deleteStock(productId);
		} catch (FeignClientException e) {
			throw handleFeignException(e);
		}
	}

	private BusinessException handleFeignException(FeignClientException e) {
		return switch (e.status()) {
			case 400 -> ProductBusinessException.from(ProductErrorCode.INVALID_STOCK_REQUEST);
			case 404 -> ProductBusinessException.from(ProductErrorCode.STOCK_NOT_FOUND);
			case 409 -> ProductBusinessException.from(ProductErrorCode.STOCK_CONFLICT);
			default -> ProductBusinessException.from(CommonErrorCode.BAD_GATEWAY);
		};
	}
}
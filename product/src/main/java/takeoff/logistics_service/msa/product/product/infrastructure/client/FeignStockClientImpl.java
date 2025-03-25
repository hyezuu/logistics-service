package takeoff.logistics_service.msa.product.product.infrastructure.client;

import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.INVALID_STOCK_REQUEST;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.STOCK_CLIENT_TIMEOUT;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.STOCK_CONFLICT;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.STOCK_NOT_FOUND;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
import takeoff.logistics_service.msa.product.product.application.service.StockClient;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request.PostStockRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeignStockClientImpl implements StockClient {

	private final FeignStockClient feignStockClient;

	@Override
	public PostStockResponseDto saveStock(PostStockRequestDto requestDto) {
		try {
			return feignStockClient.saveStock(
				PostStockRequest.from(requestDto)).toApplicationDto();
		} catch (FeignClientException e) {
			e.printStackTrace();
			throw handleFeignException(e);
		} catch (FeignException e) {
			throw ProductBusinessException.from(STOCK_CLIENT_TIMEOUT);
		}
	}

	@Override
	public void deleteStock(UUID productId) {
		try {
			feignStockClient.deleteStock(productId);
		} catch (FeignClientException e) {
			throw handleFeignException(e);
		} catch (FeignException e) {
			throw ProductBusinessException.from(STOCK_CLIENT_TIMEOUT);
		}
	}

	private BusinessException handleFeignException(FeignClientException e) {
		return ProductBusinessException.from(
			switch (e.status()) {
				case 400 -> INVALID_STOCK_REQUEST;
				case 404 -> STOCK_NOT_FOUND;
				case 409 -> STOCK_CONFLICT;
				default -> CommonErrorCode.BAD_GATEWAY;
			}
		);
	}
}
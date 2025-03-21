package takeoff.logistics_service.msa.product.stock.infrastructure.client;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetUserResponseDto;
import takeoff.logistics_service.msa.product.stock.application.exception.StockBusinessException;
import takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode;
import takeoff.logistics_service.msa.product.stock.application.service.UserClient;

@Component
@RequiredArgsConstructor
public class FeignUserClientImpl implements UserClient {

	private final FeignUserClient feignUserClient;

	@Override
	public GetUserResponseDto findByUserId(Long userId) {
		try {
			return GetUserResponseDto.from(feignUserClient.findByUserId(userId));
		} catch (FeignClientException e) {
			throw handleFeignException(e);
		}
	}

	public BusinessException handleFeignException(FeignException e) {
		return switch (e.status()) {
			case 400 -> StockBusinessException.from(StockErrorCode.INVALID_USER_REQUEST);
			case 401 -> StockBusinessException.from(StockErrorCode.UNAUTHORIZED_ACCESS);
			case 403 -> StockBusinessException.from(StockErrorCode.ACCESS_DENIED);
			case 404 -> StockBusinessException.from(StockErrorCode.USER_NOT_FOUND);
			default -> StockBusinessException.from(CommonErrorCode.BAD_GATEWAY);
		};
	}
}
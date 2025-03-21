package takeoff.logistics_service.msa.product.product.infrastructure.client;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetUserResponseDto;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
import takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode;
import takeoff.logistics_service.msa.product.product.application.service.UserClient;

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
			case 400 -> ProductBusinessException.from(ProductErrorCode.INVALID_USER_REQUEST);
			case 401 -> ProductBusinessException.from(ProductErrorCode.UNAUTHORIZED_ACCESS);
			case 403 -> ProductBusinessException.from(ProductErrorCode.ACCESS_DENIED);
			case 404 -> ProductBusinessException.from(ProductErrorCode.USER_NOT_FOUND);
			default -> ProductBusinessException.from(CommonErrorCode.BAD_GATEWAY);
		};
	}
}
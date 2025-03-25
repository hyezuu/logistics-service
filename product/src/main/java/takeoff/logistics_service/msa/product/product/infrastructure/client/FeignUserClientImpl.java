package takeoff.logistics_service.msa.product.product.infrastructure.client;

import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.ACCESS_DENIED;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.INVALID_USER_REQUEST;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.UNAUTHORIZED_ACCESS;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.USER_NOT_FOUND;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.product.application.dto.response.GetUserResponseDto;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
import takeoff.logistics_service.msa.product.product.application.service.UserClient;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response.GetUserResponse;

@RequiredArgsConstructor
@Component("productFeignUserClientImpl")
public class FeignUserClientImpl implements UserClient {

	private final FeignUserClient feignUserClient;

	@Override
	public GetUserResponseDto findByCompanyManagerId(Long managerId) {
		try {
			return feignUserClient.getUsersByCompanyManager(managerId).users().stream()
				.filter(user -> user.userId().equals(managerId))
				.findFirst()
				.map(GetUserResponse::toApplicationDto)
				.orElseThrow(() -> ProductBusinessException.from(USER_NOT_FOUND));
		} catch (FeignClientException e) {
			throw handleFeignException(e);
		}
	}

	public BusinessException handleFeignException(FeignException e) {
		return ProductBusinessException.from(
			switch (e.status()) {
				case 400 -> INVALID_USER_REQUEST;
				case 401 -> UNAUTHORIZED_ACCESS;
				case 403 -> ACCESS_DENIED;
				case 404 -> USER_NOT_FOUND;
				default -> CommonErrorCode.BAD_GATEWAY;
			}
		);
	}
}
package takeoff.logistics_service.msa.product.stock.infrastructure.client;

import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.ACCESS_DENIED;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.INVALID_USER_REQUEST;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.UNAUTHORIZED_ACCESS;
import static takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode.USER_NOT_FOUND;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetUserResponseDto;
import takeoff.logistics_service.msa.product.stock.application.exception.StockBusinessException;
import takeoff.logistics_service.msa.product.stock.application.service.UserClient;
import takeoff.logistics_service.msa.product.stock.infrastructure.client.dto.response.GetUserResponse;

@RequiredArgsConstructor
@Component("stockFeignUserClientImpl")
public class FeignUserClientImpl implements UserClient {

	private final FeignUserClient feignUserClient;

	@Override
	public GetUserResponseDto findByHubManagerId(Long managerId) {
		try {
			return feignUserClient.getUsersByHubManager(managerId).users().stream()
				.filter(user -> user.userId().equals(managerId))
				.findFirst()
				.map(GetUserResponse::toApplicationDto)
				.orElseThrow(() -> StockBusinessException.from(USER_NOT_FOUND));
		} catch (FeignClientException e) {
			throw handleFeignException(e);
		}
	}

	public BusinessException handleFeignException(FeignException e) {
		return StockBusinessException.from(
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
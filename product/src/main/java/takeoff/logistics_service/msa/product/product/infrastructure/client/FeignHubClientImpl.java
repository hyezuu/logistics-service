package takeoff.logistics_service.msa.product.product.infrastructure.client;

import feign.FeignException.FeignClientException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
import takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode;
import takeoff.logistics_service.msa.product.product.application.service.HubClient;

@Component
@RequiredArgsConstructor
public class FeignHubClientImpl implements HubClient {

	private final FeignHubClient feignHubClient;

	@Override
	public void findByHubId(UUID hubId) {
		try {
			feignHubClient.findByHubId(hubId);
		} catch (FeignClientException e) {
			throw handleFeignException(e);
		}
	}

	private BusinessException handleFeignException(FeignClientException e) {
		if (e.status() == HttpStatus.NOT_FOUND.value()) {
			return ProductBusinessException.from(ProductErrorCode.HUB_NOT_FOUND);
		} else {
			return ProductBusinessException.from(CommonErrorCode.BAD_GATEWAY);
		}
	}
}

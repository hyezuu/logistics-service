package takeoff.logistics_service.msa.product.product.infrastructure.client;

import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.HUB_NOT_FOUND;
import static takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode.INVALID_HUB_REQUEST;

import feign.FeignException.FeignClientException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
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
			e.printStackTrace();
			throw handleFeignException(e);
		}
	}

	private BusinessException handleFeignException(FeignClientException e) {
		return ProductBusinessException.from(
			switch (e.status()) {
				case 400 -> INVALID_HUB_REQUEST;
				case 404 -> HUB_NOT_FOUND;
				default -> CommonErrorCode.BAD_GATEWAY;
			}
		);
	}
}

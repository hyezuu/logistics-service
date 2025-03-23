package takeoff.logistics_service.msa.product.product.application.service;

import takeoff.logistics_service.msa.product.product.application.dto.response.GetUserResponseDto;

public interface UserClient {

	GetUserResponseDto findByCompanyManagerId(Long userId);
}

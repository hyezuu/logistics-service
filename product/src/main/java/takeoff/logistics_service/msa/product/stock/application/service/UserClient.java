package takeoff.logistics_service.msa.product.stock.application.service;

import takeoff.logistics_service.msa.product.stock.application.dto.response.GetUserResponseDto;

public interface UserClient {

	GetUserResponseDto findByHubManagerId(Long managerId);
}

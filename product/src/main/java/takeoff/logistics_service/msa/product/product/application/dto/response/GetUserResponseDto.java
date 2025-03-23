package takeoff.logistics_service.msa.product.product.application.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response.GetUserResponse;

public record GetUserResponseDto(
	Long userId, UserRole role, UUID hubId, UUID companyId) {

	public static GetUserResponseDto from(GetUserResponse response) {
		return new GetUserResponseDto(
			response.userId(), response.role(), response.hubId(), response.companyId());
	}
}

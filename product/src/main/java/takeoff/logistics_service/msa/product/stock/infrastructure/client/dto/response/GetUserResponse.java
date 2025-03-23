package takeoff.logistics_service.msa.product.stock.infrastructure.client.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetUserResponseDto;

public record GetUserResponse(
	Long userId, String username, String slackEmail, UserRole role, UUID companyId, UUID hubId) {

	public GetUserResponseDto toApplicationDto(){
		return new GetUserResponseDto(userId, role, hubId, companyId);
	}
}

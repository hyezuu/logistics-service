package takeoff.logistics_service.msa.product.stock.infrastructure.client.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserRole;

public record GetUserResponse(
	Long userId, String username, String slackEmail, UserRole role, UUID hubId, UUID companyId) {
}

package takeoff.logistics_service.msa.product.product.application.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserRole;

public record GetUserResponseDto(
	Long userId, UserRole role, UUID hubId, UUID companyId) {

}

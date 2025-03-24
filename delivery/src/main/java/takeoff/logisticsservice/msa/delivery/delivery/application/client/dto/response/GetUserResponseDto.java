package takeoff.logisticsservice.msa.delivery.delivery.application.client.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.common.domain.UserRole;

public record GetUserResponseDto(
    Long userId, UserRole role, UUID hubId, UUID companyId) {
}


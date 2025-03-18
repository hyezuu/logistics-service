package takeoff.logistics_service.msa.user.domain.service;

import takeoff.logistics_service.msa.user.domain.entity.UserRole;

public record UserSearchCondition(
        String username,
        String email,
        UserRole role
) {
}

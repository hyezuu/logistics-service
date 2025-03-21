package takeoff.logistics_service.msa.common.domain;

import java.util.Optional;

public record UserInfoDto(Long userId, UserRole role) {

	public static Optional<UserInfoDto> of(String userId, String role) {
		try {
			Long parsedUserId = Long.parseLong(userId);
			UserRole parsedRole = UserRole.valueOf(role);
			return Optional.of(new UserInfoDto(parsedUserId, parsedRole));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}
}

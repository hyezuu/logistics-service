package takeoff.logistics_service.msa.common.domain;

public record UserInfoDto(Long userId, UserRole role) {

	public static UserInfoDto of(String userId, String role) {
		if(userId == null || role == null) {
			return empty();
		}
		return parseUserInfo(userId, role);
	}

	public static UserInfoDto empty() {
		return new UserInfoDto(null, null);
	}

	private static UserInfoDto parseUserInfo(String userId, String role) {
		try {
			return new UserInfoDto(Long.parseLong(userId), UserRole.valueOf(role));
		} catch (IllegalArgumentException e) {
			return empty();
		}
	}
}
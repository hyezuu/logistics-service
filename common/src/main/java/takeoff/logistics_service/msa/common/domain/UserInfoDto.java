package takeoff.logistics_service.msa.common.domain;

public record UserInfoDto(Long userId, UserRole role) {

	public static UserInfoDto of(String userId, String role) {
		if(userId == null || role == null) {
			return empty();
		}
		return parseUserInfo(userId, role);
	}

	private static UserInfoDto empty() {
		return new UserInfoDto(null, null);
	}

	private static UserInfoDto parseUserInfo(String userId, String role) {
		try {
			return new UserInfoDto(Long.parseLong(userId), UserRole.valueOf(role));
		} catch (IllegalArgumentException e) {
			return empty();
		}
	}

	public boolean isValid() {
		return userId != null && userId > 0 && role != null;
	}

	public boolean isAdmin() {
		return role == UserRole.MASTER_ADMIN;
	}

	public boolean isCompanyManager() {
		return role == UserRole.COMPANY_MANAGER;
	}

	public boolean isHubManager() {
		return role == UserRole.HUB_MANAGER;
	}
}
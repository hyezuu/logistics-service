package takeoff.logistics_service.msa.user.domain.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    MASTER_ADMIN(Authority.MASTER_ADMIN),
    HUB_MANAGER(Authority.HUB_MANAGER),
    COMPANY_MANAGER(Authority.COMPANY_MANAGER),
    HUB_DELIVERY_MANAGER(Authority.HUB_DELIVERY_MANAGER),
    COMPANY_DELIVERY_MANAGER(Authority.COMPANY_DELIVERY_MANAGER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MASTER_ADMIN = "ROLE_MASTER_ADMIN";
        public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
        public static final String COMPANY_MANAGER = "ROLE_COMPANY_MANAGER";
        public static final String HUB_DELIVERY_MANAGER = "ROLE_HUB_DELIVERY_MANAGER";
        public static final String COMPANY_DELIVERY_MANAGER = "ROLE_COMPANY_DELIVERY_MANAGER";
    }
}

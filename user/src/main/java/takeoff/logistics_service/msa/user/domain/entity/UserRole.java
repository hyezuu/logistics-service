package takeoff.logistics_service.msa.user.domain.entity;

public enum UserRole {
    MASTER_ADMIN, // 전체 관리자
    HUB_MANAGER, // 허브 관리자
    COMPANY_MANAGER, // 업체 관리자
    HUB_DELIVERY_MANAGER, // 허브 배송 담당자
    COMPANY_DELIVERY_MANAGER ;// 업체 배송 담당자
    public boolean isDeliveryManager() {
        return this == HUB_DELIVERY_MANAGER || this == COMPANY_DELIVERY_MANAGER;
    }
}

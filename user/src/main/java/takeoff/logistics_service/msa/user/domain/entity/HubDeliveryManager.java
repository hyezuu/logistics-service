package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

@Entity
@DiscriminatorValue("HUB_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Table(name = "p_hub_delivery_manager")
public class HubDeliveryManager extends DeliveryManager {

    private HubDeliveryManager(String username, String email, String password, UserRole role, CompanyId companyId, HubId hubId, DeliverySequence deliverySequence) {
        super(username, email, password, role, companyId, hubId, deliverySequence);
    }

    public static HubDeliveryManager create(String username, String email, String password, UserRole role, CompanyId companyId, HubId hubId, DeliverySequence deliverySequence) {
        return new HubDeliveryManager(username, email, password, role, companyId, hubId, deliverySequence);
    }
}

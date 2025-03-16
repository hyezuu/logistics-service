package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

@Entity
@DiscriminatorValue("COMPANY_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Table(name = "p_company_delivery_manager")
public class CompanyDeliveryManager extends DeliveryManager {

    private CompanyDeliveryManager(String username, String email, String password, UserRole role, CompanyId companyId, HubId hubId, DeliverySequence deliverySequence) {
        super(username, email, password, role, companyId, hubId, deliverySequence);
    }

    public static CompanyDeliveryManager create(String username, String email, String password, UserRole role, CompanyId companyId, HubId hubId, DeliverySequence deliverySequence) {
        return new CompanyDeliveryManager(username, email, password, role, companyId, hubId, deliverySequence);
    }
}

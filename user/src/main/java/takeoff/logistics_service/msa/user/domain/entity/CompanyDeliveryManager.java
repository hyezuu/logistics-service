package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;

import java.util.UUID;

@Entity
@DiscriminatorValue("COMPANY_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_delivery_manager")
@AttributeOverride(name = "companyId.companyIdentifier", column = @Column(name = "company_id"))
public class CompanyDeliveryManager extends DeliveryManager {
    @Embedded
    private CompanyId companyId;

    protected CompanyDeliveryManager(String username, String slackEmail, String password, UserRole role, CompanyId companyId, DeliverySequence deliverySequence) {
        super(username, slackEmail, password, role, deliverySequence, DeliveryManagerType.COMPANY_DELIVERY_MANAGER);
        this.companyId = companyId;
    }
    @Override
    public String getIdentifier() {
        return this.companyId.getCompanyIdentifier().toString();
    }
    public void updateIdentifier(String identifier) {
        this.companyId = CompanyId.from(UUID.fromString(identifier));
    }

    public static CompanyDeliveryManager create(String username, String slackEmail, String password, UserRole role, CompanyId companyId, DeliverySequence deliverySequence) {
        return new CompanyDeliveryManager(username, slackEmail, password, role, companyId, deliverySequence);
    }
}

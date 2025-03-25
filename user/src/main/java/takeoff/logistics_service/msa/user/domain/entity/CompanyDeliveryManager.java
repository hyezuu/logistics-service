package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

@Entity
@DiscriminatorValue("COMPANY_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_delivery_manager")
@AttributeOverride(name = "hubId.hubIdentifier", column = @Column(name = "hub_id"))
public class CompanyDeliveryManager extends DeliveryManager {

    @Embedded
    private HubId hubId;

    protected CompanyDeliveryManager(String username, String slackEmail, String password, UserRole role, HubId hubId, DeliverySequence deliverySequence) {
        super(username, slackEmail, password, role, deliverySequence, DeliveryManagerType.COMPANY_DELIVERY_MANAGER);
        this.hubId = hubId;
    }

    @Override
    public String getIdentifier() {
        return this.hubId != null ? this.hubId.getHubIdentifier().toString() : null;
    }

    @Override
    public void updateIdentifier(String identifier) {
        this.hubId = HubId.from(UUID.fromString(identifier));  // UUID로 변경하여 HubId 설정
    }

    public static CompanyDeliveryManager create(String username, String slackEmail, String password, UserRole role, HubId hubId, DeliverySequence deliverySequence) {
        return new CompanyDeliveryManager(username, slackEmail, password, role, hubId, deliverySequence);
    }
}

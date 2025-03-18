package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

import java.util.UUID;

@Entity
@DiscriminatorValue("HUB_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_delivery_manager")
@AttributeOverride(name = "hubId.hubIdentifier", column = @Column(name = "hub_id"))
public class HubDeliveryManager extends DeliveryManager {
    @Embedded
    private HubId hubId;

    protected HubDeliveryManager(String username, String slackEmail, String password, UserRole role, HubId hubId, DeliverySequence deliverySequence) {
        super(username, slackEmail, password, role, deliverySequence, DeliveryManagerType.HUB_DELIVERY_MANAGER);
        this.hubId = hubId;
    }
    @Override
    public String getIdentifier() {
        return this.hubId.getHubIdentifier().toString();
    }
    public void updateIdentifier(String identifier) {
        this.hubId = HubId.from(UUID.fromString(identifier));
    }

    public static HubDeliveryManager create(String username, String slackEmail, String password, UserRole role, HubId hubId, DeliverySequence deliverySequence) {
        return new HubDeliveryManager(username, slackEmail, password, role, hubId, deliverySequence);
    }
}

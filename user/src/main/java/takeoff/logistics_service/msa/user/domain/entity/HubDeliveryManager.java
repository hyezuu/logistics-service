package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

@Entity
@DiscriminatorValue("HUB_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_delivery_manager")
@AttributeOverride(name = "hubIdentifier", column = @Column(name = "hub_id"))
public class HubDeliveryManager extends DeliveryManager {
    @Embedded
    private HubId hubId;

    @Builder
    private HubDeliveryManager(String username, String slackEmail, String password, UserRole role, HubId hubId, DeliverySequence deliverySequence) {
        super(username, slackEmail, password, role, deliverySequence, DeliveryManagerType.HUB_DELIVERY_MANAGER);
        this.hubId = hubId;
    }

    public String getHubIdentifier() {
        return hubId.getHubIdentifier().toString();
    }

    public static HubDeliveryManager create(String username, String slackEmail, String password, UserRole role, HubId hubId, DeliverySequence deliverySequence) {
        return HubDeliveryManager.builder()
                .username(username)
                .slackEmail(slackEmail)
                .password(password)
                .role(role)
                .hubId(hubId)
                .deliverySequence(deliverySequence)
                .build();
    }
}

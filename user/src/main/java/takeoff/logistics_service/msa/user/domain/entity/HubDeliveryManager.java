package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.*;

import java.util.Optional;
import java.util.UUID;

@Entity
@DiscriminatorValue("HUB_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_delivery_manager")
@AttributeOverride(name = "hubIdentifier", column = @Column(name = "hub_id"))
public class HubDeliveryManager extends DeliveryManager {
    @Embedded
    private HubId hubId;

    @Builder
    private HubDeliveryManager(String username, String email, String password, UserRole role, SlackId slackId, HubId hubId, DeliverySequence deliverySequence) {
        super(username, email, password, role, slackId, deliverySequence, DeliveryManagerType.HUB_DELIVERY_MANAGER);
        this.hubId = hubId;
    }

    public String getHubIdentifier() {
        return hubId.getHubIdentifier().toString();
    }

    public static HubDeliveryManager create(String username, String email, String password, UserRole role, SlackId slackId, HubId hubId, DeliverySequence deliverySequence) {
        return HubDeliveryManager.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .slackId(slackId)
                .hubId(hubId)
                .deliverySequence(deliverySequence)
                .build();
    }
}

package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.SlackId;

import java.util.Optional;

@Entity
@Getter
@DiscriminatorColumn(name = "delivery_manager_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_delivery_manager")
public abstract class DeliveryManager extends User {

    @Embedded
    private DeliverySequence deliverySequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_manager_type", nullable = false)
    private DeliveryManagerType deliveryManagerType;

    protected DeliveryManager(String username, String email, String password, UserRole role, SlackId slackId, DeliverySequence deliverySequence, DeliveryManagerType deliveryManagerType) {
        super(username, email, password, role, slackId);
        this.deliverySequence = deliverySequence;
        this.deliveryManagerType = deliveryManagerType;
    }

    public boolean isHubDeliveryManager() {
        return this.deliveryManagerType == DeliveryManagerType.HUB_DELIVERY_MANAGER;
    }

    public boolean isCompanyDeliveryManager() {
        return this.deliveryManagerType == DeliveryManagerType.COMPANY_DELIVERY_MANAGER;
    }

    public String getHubIdentifier() {
        return null;
    }

    public String getCompanyIdentifier() {
        return null;
    }
}

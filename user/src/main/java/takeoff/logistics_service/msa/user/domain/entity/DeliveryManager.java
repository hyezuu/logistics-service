package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_delivery_manager")
public abstract class DeliveryManager extends User {

    @Embedded
    private DeliverySequence deliverySequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_manager_type", nullable = false)
    private DeliveryManagerType deliveryManagerType;

    protected DeliveryManager(String username, String slackEmail, String password, UserRole role, DeliverySequence deliverySequence, DeliveryManagerType deliveryManagerType) {
        super(username, slackEmail, password, role);
        this.deliverySequence = deliverySequence;
        this.deliveryManagerType = deliveryManagerType;
    }

    public static DeliveryManager create(String username, String slackEmail, String password, UserRole role,
                                         UUID identifier, DeliverySequence deliverySequence, DeliveryManagerType type) {
        if (type == DeliveryManagerType.HUB_DELIVERY_MANAGER) {
            return new HubDeliveryManager(username, slackEmail, password, role, deliverySequence,identifier);
        } else if (type == DeliveryManagerType.COMPANY_DELIVERY_MANAGER) {
            return new CompanyDeliveryManager(username, slackEmail, password, role, HubId.from(identifier), deliverySequence);
        }
        throw new IllegalArgumentException("Invalid Delivery Manager Type");
    }

    public abstract String getIdentifier();  // 서브 클래스에서 구현하도록 강제

    public abstract void updateIdentifier(String identifier);

    public void updateDeliveryManager(String slackEmail, DeliverySequence deliverySequence){
        updateSlackEmail(slackEmail);
        this.deliverySequence = deliverySequence;
    }

    public void deleteDeliveryManager(Long deletedByUserId) {
        super.delete(deletedByUserId);
    }

}

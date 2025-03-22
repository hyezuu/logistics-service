package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

import java.util.UUID;

@Entity
@DiscriminatorValue("HUB_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_delivery_manager")
public class HubDeliveryManager extends DeliveryManager {

    protected HubDeliveryManager(String username, String slackEmail, String password, UserRole role, DeliverySequence deliverySequence) {
        super(username, slackEmail, password, role, deliverySequence, DeliveryManagerType.HUB_DELIVERY_MANAGER);
    }
    @Override
    public String getIdentifier() {
        return null;
    }
    public void updateIdentifier(String identifier) {
        throw new UnsupportedOperationException("허브 배송 담당자는 식별자를 변경할 수 없습니다.");
    }
    public static HubDeliveryManager create(String username, String slackEmail, String password, UserRole role, DeliverySequence deliverySequence) {
        return new HubDeliveryManager(username, slackEmail, password, role, deliverySequence);
    }
}

package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_delivery_manager")
@MappedSuperclass
public abstract class DeliveryManager extends User {

    @Embedded
    private DeliverySequence deliverySequence;

    protected DeliveryManager(String username, String email, String password, UserRole role, CompanyId companyId, HubId hubId, DeliverySequence deliverySequence) {
        super(username, email, password, role, companyId, hubId);
        this.deliverySequence = deliverySequence;
    }
}

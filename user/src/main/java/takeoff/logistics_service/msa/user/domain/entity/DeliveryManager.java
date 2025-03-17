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

    protected DeliveryManager(String username, String slackEmail, String password, UserRole role, DeliverySequence deliverySequence, DeliveryManagerType deliveryManagerType) {
        super(username, slackEmail, password, role);
        this.deliverySequence = deliverySequence;
        this.deliveryManagerType = deliveryManagerType;
    }

}

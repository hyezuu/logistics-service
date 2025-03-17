package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.*;

import java.util.Optional;
import java.util.UUID;

@Entity
@DiscriminatorValue("COMPANY_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_delivery_manager")
@AttributeOverride(name = "companyIdentifier", column = @Column(name = "company_id"))
public class CompanyDeliveryManager extends DeliveryManager {

    @Embedded
    private CompanyId companyId;
    @Builder
    private CompanyDeliveryManager(String username, String email, String password, UserRole role, SlackId slackId, CompanyId companyId, DeliverySequence deliverySequence) {
        super(username, email, password, role, slackId, deliverySequence, DeliveryManagerType.COMPANY_DELIVERY_MANAGER);
        this.companyId = companyId;
    }

    public String getCompanyIdentifier() {
        return companyId.getCompanyIdentifier().toString();
    }

    public static CompanyDeliveryManager create(String username, String email, String password, UserRole role, SlackId slackId, CompanyId companyId, DeliverySequence deliverySequence) {
        return CompanyDeliveryManager.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .slackId(slackId)
                .companyId(companyId)
                .deliverySequence(deliverySequence)
                .build();
    }
}

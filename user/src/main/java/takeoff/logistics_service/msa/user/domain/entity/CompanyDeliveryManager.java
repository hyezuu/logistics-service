package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;

@Entity
@DiscriminatorValue("COMPANY_DELIVERY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_delivery_manager")
@AttributeOverride(name = "companyIdentifier", column = @Column(name = "company_id"))
public class CompanyDeliveryManager extends DeliveryManager {

    @Embedded
    private CompanyId companyId;
    @Builder
    private CompanyDeliveryManager(String username, String slackEmail, String password, UserRole role, CompanyId companyId, DeliverySequence deliverySequence) {
        super(username, slackEmail, password, role, deliverySequence, DeliveryManagerType.COMPANY_DELIVERY_MANAGER);
        this.companyId = companyId;
    }

    public String getCompanyIdentifier() {
        return companyId.getCompanyIdentifier().toString();
    }

    public static CompanyDeliveryManager create(String username, String slackEmail, String password, UserRole role, CompanyId companyId, DeliverySequence deliverySequence) {
        return CompanyDeliveryManager.builder()
                .username(username)
                .slackEmail(slackEmail)
                .password(password)
                .role(role)
                .companyId(companyId)
                .deliverySequence(deliverySequence)
                .build();
    }
}

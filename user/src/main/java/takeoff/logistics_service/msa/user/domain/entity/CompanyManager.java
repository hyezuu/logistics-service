package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.application.dto.GetCompanyFeignResponse;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;


@Entity
@Getter
@DiscriminatorValue("COMPANY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_manager")
@AttributeOverride(name = "companyId.companyIdentifier", column = @Column(name = "company_id"))
@AttributeOverride(name = "hubId.hubIdentifier", column = @Column(name = "hub_id"))
public class CompanyManager extends Employee {

    @Embedded
    private CompanyId companyId;

    protected CompanyManager(String username, String slackEmail, String password, UserRole role, CompanyId companyId, HubId hubId) {
        super(username, slackEmail, password, role);
        this.companyId = companyId;
    }

    public static CompanyManager createFromFeign(String username, String slackEmail, String password, UserRole role, GetCompanyFeignResponse companyResponse) {
        return new CompanyManager(
                username,
                slackEmail,
                password,
                role,
                CompanyId.from(companyResponse.companyId()),
                HubId.from(companyResponse.hubId())
        );
    }

    public void updateCompanyId(UUID newCompanyId) {
        this.companyId = CompanyId.from(newCompanyId);
    }

    @Override
    public String getIdentifier() {
        return companyId.getCompanyIdentifier().toString();
    }
}

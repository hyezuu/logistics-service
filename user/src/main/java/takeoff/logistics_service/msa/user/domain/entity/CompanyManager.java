package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.application.dto.GetCompanyFeignResponse;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

import java.util.UUID;


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
    @Embedded
    private HubId hubId;

    protected CompanyManager(String username, String slackEmail, String password, UserRole role, CompanyId companyId, HubId hubId) {
        super(username, slackEmail, password, role);
        this.companyId = companyId;
        this.hubId = hubId;
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

    public void updateHubId(UUID newHubId) {
        this.hubId = HubId.from(newHubId);
    }

    @Override
    public String getIdentifier() {
        return companyId.getCompanyIdentifier().toString();
    }
}

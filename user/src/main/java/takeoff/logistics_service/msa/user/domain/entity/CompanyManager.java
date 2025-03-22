package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;

import java.util.UUID;

@Entity
@DiscriminatorValue("COMPANY_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company_manager")
@AttributeOverride(name = "companyId.companyIdentifier", column = @Column(name = "company_id"))
public class CompanyManager extends User {

    @Embedded
    private CompanyId companyId;

    protected CompanyManager(String username, String slackEmail, String password, UserRole role, CompanyId companyId) {
        super(username, slackEmail, password, role);
        this.companyId = companyId;
    }

    public static CompanyManager create(String username, String slackEmail, String password, UserRole role, UUID companyId) {
        return new CompanyManager(username, slackEmail, password, role, CompanyId.from(companyId));
    }

    public CompanyId getCompanyId() {
        return companyId;
    }

    public void updateCompanyId(UUID newId) {
        this.companyId = CompanyId.from(newId);
    }
}

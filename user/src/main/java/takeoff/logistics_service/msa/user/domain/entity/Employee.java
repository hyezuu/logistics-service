package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("EMPLOYEE")
@Table(name = "p_employee")
public abstract class Employee extends User {

    protected Employee(String username, String slackEmail, String password, UserRole role) {
        super(username, slackEmail, password, role);
    }

    public CompanyId getCompanyId() {
        return null;
    }
    public HubId getHubId() {
        return null;
    }

    public abstract String getIdentifier();
}

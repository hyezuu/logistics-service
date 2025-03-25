package takeoff.logistics_service.msa.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CompanyId {

    @Column(name = "company_id", nullable = false)
    private UUID companyIdentifier;

    private CompanyId(UUID companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public static CompanyId from(UUID companyIdentifier) {
        return new CompanyId(companyIdentifier);
    }
}

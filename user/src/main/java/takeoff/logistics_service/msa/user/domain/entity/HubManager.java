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
import takeoff.logistics_service.msa.user.application.dto.GetHubFeignResponse;
import takeoff.logistics_service.msa.user.domain.vo.HubId;


@Entity
@Getter
@DiscriminatorValue("HUB_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_manager")
@AttributeOverride(name = "hubId.hubIdentifier", column = @Column(name = "hub_id"))
public class HubManager extends Employee {

    @Embedded
    private HubId hubId;

    protected HubManager(String username, String slackEmail, String password, UserRole role, HubId hubId) {
        super(username, slackEmail, password, role);
        this.hubId = hubId;
    }

    public static HubManager createFromFeign(String username, String slackEmail, String password, UserRole role, GetHubFeignResponse hubResponse) {
        return new HubManager(
                username,
                slackEmail,
                password,
                role,
                HubId.from(hubResponse.hubId())
        );
    }

    public void updateHubId(UUID newHubId) {
        this.hubId = HubId.from(newHubId);
    }

    @Override
    public String getIdentifier() {
        return hubId.getHubIdentifier().toString();
    }
}

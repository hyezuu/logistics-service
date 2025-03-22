package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

import java.util.UUID;

@Entity
@DiscriminatorValue("HUB_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_manager")
@AttributeOverride(name = "hubId.hubIdentifier", column = @Column(name = "hub_id"))
public class HubManager extends User {

    @Embedded
    private HubId hubId;

    protected HubManager(String username, String slackEmail, String password, UserRole role, HubId hubId) {
        super(username, slackEmail, password, role);
        this.hubId = hubId;
    }

    public static HubManager create(String username, String slackEmail, String password, UserRole role, UUID hubId) {
        return new HubManager(username, slackEmail, password, role, HubId.from(hubId));
    }

    public HubId getHubId() {
        return hubId;
    }

    public void updateHubId(UUID newId) {
        this.hubId = HubId.from(newId);
    }
}

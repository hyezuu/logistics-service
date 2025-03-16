package takeoff.logistics_service.msa.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class HubId {

    @Column(name = "hub_id", nullable = false)
    private UUID value;

    private HubId(UUID value) {
        this.value = value;
    }

    public static HubId from(UUID value) {
        return new HubId(value);
    }
}

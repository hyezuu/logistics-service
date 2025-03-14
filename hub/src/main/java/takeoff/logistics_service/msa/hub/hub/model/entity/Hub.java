package takeoff.logistics_service.msa.hub.hub.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 14.
 */
@Entity
@Table(name = "p_hub")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "hub_name", nullable = false)
    private String hubName;

    @Embedded
    private Location location;

    public void modifyHubName(String hubName) {
        this.hubName = hubName;
    }

    @Builder
    private Hub(String hubName, Location location) {
        this.hubName = hubName;
        this.location = location;
    }
}

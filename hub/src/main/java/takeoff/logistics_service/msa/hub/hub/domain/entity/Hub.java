package takeoff.logistics_service.msa.hub.hub.domain.entity;

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
import takeoff.logistics_service.msa.common.domain.BaseEntity;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 14.
 */
@Entity
@Table(name = "p_hub")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseEntity {

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


    private Hub(String hubName, Location location) {
        this.hubName = hubName;
        this.location = location;
    }

    //테스트용 생성자
    private Hub(UUID id, String hubName, Location location) {
        this.id = id;
        this.hubName = hubName;
        this.location = location;
    }
}

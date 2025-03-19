package takeoff.logistics_service.msa.hub.hubroute.domain.entity;

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
 * @Date : 2025. 03. 15.
 */
@Entity
@Table(name = "p_hubRoute")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubRoute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID fromHubId;
    private UUID toHubId;

    @Embedded
    private Distance distance;
    @Embedded
    private Duration duration;

    //수정 메서드
    public void modifyHubRoute(UUID fromHubId, UUID toHubId,int kilometers, int minutes) {
        if (fromHubId == null || toHubId == null) {
            throw new IllegalArgumentException("허브 ID는 null일 수 없습니다.");
        }
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
        Distance.create(kilometers);
        Duration.create(minutes);
    }

    @Builder
    private HubRoute(UUID fromHubId, UUID toHubId, Distance distance, Duration duration) {
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
        this.distance = distance;
        this.duration = duration;
    }
}

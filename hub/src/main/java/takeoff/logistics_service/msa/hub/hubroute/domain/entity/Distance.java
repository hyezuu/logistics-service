package takeoff.logistics_service.msa.hub.hubroute.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distance {

    private int distanceKm;


    public static Distance create(int kilometers) {
        if (kilometers < 0) {
            throw new IllegalArgumentException("거리는 0보다 커야 합니다.");
        }
        return new Distance(kilometers);
    }

    private Distance(int kilometers) {
        this.distanceKm = kilometers;
    }
}

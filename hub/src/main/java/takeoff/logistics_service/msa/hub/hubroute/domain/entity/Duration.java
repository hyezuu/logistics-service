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
public class Duration {

    private int durationMinutes;


    public static Duration create(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("소요 시간은 0보다 커야 합니다.");
        }
        return new Duration(minutes);
    }
    private Duration(int minutes) {
        this.durationMinutes = minutes;
    }
}

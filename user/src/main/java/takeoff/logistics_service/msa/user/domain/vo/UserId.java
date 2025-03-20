package takeoff.logistics_service.msa.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class UserId {

    @Column(name = "user_id", nullable = false)
    private Long id;
    private UserId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("UserId 값이 유효하지 않습니다.");
        }
        this.id = id;
    }
    public static UserId from(Long id) {
        return new UserId(id);
    }
    @Override
    public String toString() {
        return id.toString();
    }
}

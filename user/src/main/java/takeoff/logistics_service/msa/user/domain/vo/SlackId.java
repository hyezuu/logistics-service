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
public class SlackId {

    @Column(name = "slack_id", nullable = false, unique = true)
    private UUID value;

    public SlackId(UUID value) {
        validate(value);
        this.value = value;
    }

    private void validate(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Slack ID는 NULL일 수 없습니다.");
        }
    }
}

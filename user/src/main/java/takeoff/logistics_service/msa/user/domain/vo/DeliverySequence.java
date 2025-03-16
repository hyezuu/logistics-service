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
public class DeliverySequence {

    @Column(name = "delivery_sequence", nullable = false)
    private int value;

    private DeliverySequence(int value) {
        this.value = value;
    }

    public static DeliverySequence from(int value) {
        return new DeliverySequence(value);
    }
}

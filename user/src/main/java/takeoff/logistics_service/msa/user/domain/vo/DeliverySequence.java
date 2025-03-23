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
    private int sequenceNumber;

    private DeliverySequence(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public static DeliverySequence from(int sequenceNumber) {
        return new DeliverySequence(sequenceNumber);
    }
}

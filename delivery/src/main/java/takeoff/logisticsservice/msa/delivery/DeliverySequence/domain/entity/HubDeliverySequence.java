package takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "p_hub_delivery_sequence")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubDeliverySequence {

  @Id
  @Column(name = "delivery_sequence_id")
  @UuidGenerator
  private UUID id;

  @Column(name = "current_sequence", nullable = false)
  private Integer sequence;

  public void modifySequence(Integer nextSequence) {
    if (nextSequence == null) {
      throw new IllegalArgumentException("sequence is null");
    }

    if (nextSequence < 0) {
      throw new IllegalArgumentException("sequence is negative");
    }

    this.sequence = nextSequence;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HubDeliverySequence that)) {
      return false;
    }
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}

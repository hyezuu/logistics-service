package takeoff.logisticsservice.msa.delivery.delivery.domain.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class DeliveryId implements Serializable {

  @UuidGenerator
  @Getter
  private UUID id;

  public static DeliveryId from(UUID id) {
    return new DeliveryId(id);
  }

  public DeliveryId(UUID id) {
    this.id = id;
  }
}

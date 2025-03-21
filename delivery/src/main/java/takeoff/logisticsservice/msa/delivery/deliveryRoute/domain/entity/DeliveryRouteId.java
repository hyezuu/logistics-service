package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

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
public class DeliveryRouteId implements Serializable {

  @UuidGenerator
  @Getter
  private UUID id;

  public static DeliveryRouteId from(UUID id) {
    return new DeliveryRouteId(id);
  }

  public DeliveryRouteId(UUID id) {
    this.id = id;
  }
}


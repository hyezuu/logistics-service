package takeoff.logistics_service.msa.order.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OrderId implements Serializable {

  @UuidGenerator
  @Column(name = "order_id", nullable = false)
  private UUID orderId;

  public static OrderId from(UUID orderId) {
    return new OrderId(orderId);
  }

  public OrderId(UUID orderId) {
    this.orderId = orderId;
  }
}

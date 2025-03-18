package takeoff.logisticsservice.msa.delivery.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "p_delivery")
@Getter
@Builder
public class Delivery {

  @EmbeddedId
  private DeliveryId id;

  @Column(name = "order_id", nullable = false)
  private UUID orderId;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  @Column(name = "from_hub_id", nullable = false)
  private UUID fromHubId;

  @Column(name = "to_hub_id", nullable = false)
  private UUID toHubId;

  public Delivery() {
    this.status = DeliveryStatus.ORDERED;
    // TODO : 배송 경로 관련 로직 추가 + 허브 서비스와 연결
    this.fromHubId = UUID.randomUUID();
    this.toHubId = UUID.randomUUID();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Delivery delivery)) {
      return false;
    }
    return Objects.equals(id, delivery.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}

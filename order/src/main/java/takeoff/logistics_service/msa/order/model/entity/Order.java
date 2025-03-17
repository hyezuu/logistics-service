package takeoff.logistics_service.msa.order.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "p_order")
@Getter
@Builder
public class Order {

  @EmbeddedId
  private OrderId id;

  @Column(name = "supplier_id", nullable = false)
  private UUID supplierId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  @Column(name = "customer_id", nullable = false)
  private Long customerId;

  @Column(name = "delivery_id", nullable = false)
  private UUID deliveryId;

  @Column(name = "delivery_address", nullable = false)
  private String address;

  @Column(name = "request_notes")
  private String requestNotes;

  public void addOrderItems(List<OrderItem> orderItems) {
    for (OrderItem orderItem : orderItems) {
      orderItem.enrollOrder(this);
      this.orderItems.add(orderItem);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Order order)) {
      return false;
    }
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}

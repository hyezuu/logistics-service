package takeoff.logistics_service.msa.order.domain.entity;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.common.domain.BaseEntity;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

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

  @Column(name = "managed_hub_id", nullable = false)
  private UUID managedHubId;

  @Column(name = "delivery_address", nullable = false)
  private String address;

  @Column(name = "request_notes")
  private String requestNotes;

  @Builder
  Order(UUID id, UUID supplierId, List<OrderItem> orderItems,
      Long customerId,  String address, String requestNotes) {
    this.id = OrderId.from(id);
    this.supplierId = supplierId;
    this.customerId = customerId;
    this.address = address;
    this.requestNotes = requestNotes;

    this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
    this.orderItems.forEach(item -> item.enrollOrder(this));
  }

  public void registerHub(UUID managedHubId) {
    this.managedHubId = managedHubId;
  }


  public void modifyDeliveryId(UUID deliveryId) {
    this.deliveryId = deliveryId;
  }

  public void modifyAllQuantityByProduct(List<ModifyQuantityCommand> commands) {
    orderItems.forEach(orderItem -> {
      ModifyQuantityCommand command = commands.stream()
          .filter(c -> c.productId().equals(orderItem.getProductId()))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("주문 상품을 찾을 수 없습니다."));

      orderItem.modifyQuantity(command.quantity());
    });
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

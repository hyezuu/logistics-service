package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

import static takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRouteStatus.Status.ARRIVED_AT_HUB;
import static takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRouteStatus.Status.DELIVERING_COMPLETED;
import static takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRouteStatus.Status.IN_TRANSIENT;
import static takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRouteStatus.Status.READY_FOR_DELIVERY;
import static takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity.DeliveryRouteStatus.Status.WAITING_HUB;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery_route")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRoute {

  @EmbeddedId
  @Column(name = "delivery_route_id")
  private DeliveryRouteId id;

  @Column(name = "delivery_id", nullable = false)
  private UUID deliveryId;

  @Column(name = "deliveryManager_Id", nullable = false)
  private Long deliveryManagerId;

  @Column(name = "sequence_number", nullable = false)
  private Integer sequenceNumber;

  @Column(name = "from_hub_id", nullable = false)
  private UUID fromHubId;

  @Column(name = "to_hub_id", nullable = false)
  private UUID toHubId;

  @Embedded
  private EstimatedArrivalInfo estimatedArrivalInfo;

  @Embedded
  private ActualArrivalInfo actualArrivalInfo;

  @Column(name = "current_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private DeliveryRouteStatus status;

  @Builder
  public DeliveryRoute(UUID deliveryId, Long deliveryManagerId, Integer sequenceNumber,
      UUID fromHubId, UUID toHubId, String status, Integer estimatedDuration,
      Integer estimatedDistance
  ) {
    this.deliveryId = deliveryId;
    this.deliveryManagerId = deliveryManagerId;
    this.sequenceNumber = sequenceNumber;
    this.fromHubId = fromHubId;
    this.toHubId = toHubId;
    modifyStatus(status);
    this.estimatedArrivalInfo = EstimatedArrivalInfo.of(estimatedDuration, estimatedDistance);
  }

  private void modifyStatus(String status) {
    switch (status) {
      case WAITING_HUB -> this.status = DeliveryRouteStatus.WAITING_HUB;
      case IN_TRANSIENT -> this.status = DeliveryRouteStatus.IN_TRANSIENT;
      case ARRIVED_AT_HUB -> this.status = DeliveryRouteStatus.ARRIVED_AT_HUB;
      case READY_FOR_DELIVERY -> this.status = DeliveryRouteStatus.READY_FOR_DELIVERY;
      case DELIVERING_COMPLETED -> this.status = DeliveryRouteStatus.DELIVERING_COMPLETED;
      default -> throw new IllegalArgumentException("Invalid status: " + status);
      // TODO : 글로벌 예외로 변경
    }
  }
}

package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class EstimatedArrivalInfo {

  @Column(name = "estimated_distance", nullable = false)
  private Integer estimatedDistance;

  @Column(name = "estimated_duration", nullable = false)
  private Integer estimatedDuration;

  public static EstimatedArrivalInfo of(Integer estimatedDuration, Integer estimatedDistance) {
    return new EstimatedArrivalInfo(estimatedDuration, estimatedDistance);
  }

  private EstimatedArrivalInfo(Integer estimatedDuration, Integer estimatedDistance) {
    this.estimatedDuration = estimatedDuration;
    this.estimatedDistance = estimatedDistance;
  }
}

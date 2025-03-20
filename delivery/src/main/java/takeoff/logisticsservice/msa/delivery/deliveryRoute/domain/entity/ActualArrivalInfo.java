package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActualArrivalInfo {

  @Column(name = "actual_distance", nullable = false)
  private Integer actualDistance;

  @Column(name = "actual_duration", nullable = false)
  private Integer actualDuration;
}

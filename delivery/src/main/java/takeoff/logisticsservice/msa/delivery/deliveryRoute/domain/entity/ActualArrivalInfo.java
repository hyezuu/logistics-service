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

  @Column(name = "actual_distance")
  private Integer actualDistance;

  @Column(name = "actual_duration")
  private Integer actualDuration;

  public static ActualArrivalInfo of(Integer actualDuration, Integer actualDistance) {
    return new ActualArrivalInfo(actualDuration, actualDistance);
  }

  private ActualArrivalInfo(Integer actualDuration, Integer actualDistance) {
    this.actualDistance = actualDistance;
    this.actualDuration = actualDuration;
  }
}

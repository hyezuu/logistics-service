package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response;

import java.util.Objects;
import java.util.UUID;

public record FindHubRoutes(
    UUID hubRouteId,
    UUID fromHubId,
    UUID toHubId,
    DistanceApi distanceApi,
    DurationApi durationApi
) {

  public int getDistance() {
    return distanceApi.distance();
  }

  public int getDuration() {
    return durationApi.duration();
  }

  private record DistanceApi(int distance) {

    public DistanceApi(int distance) {
      this.distance = distance / 1000;
    }
  }

  private record DurationApi(int duration) {

    public DurationApi(int duration) {
      this.duration = duration / 60;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FindHubRoutes findHubRoutes)) {
      return false;
    }
    return Objects.equals(hubRouteId, findHubRoutes.hubRouteId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(hubRouteId);
  }
}

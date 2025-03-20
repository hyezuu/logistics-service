package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

public enum DeliveryRouteStatus {
  WAITING_HUB(Status.WAITING_HUB),
  IN_TRANSIENT(Status.IN_TRANSIENT),
  ARRIVED_AT_HUB(Status.ARRIVED_AT_HUB),
  READY_FOR_DELIVERY(Status.READY_FOR_DELIVERY),
  DELIVERING_COMPLETED(Status.DELIVERING_COMPLETED);


  private final String status;

  public String getStatus() {
    return this.status;
  }

  DeliveryRouteStatus(String status) {
    this.status = status;
  }

  public static class Status {

    public static final String WAITING_HUB = "ORDERED";
    public static final String IN_TRANSIENT = "IN_TRANSIENT";
    public static final String ARRIVED_AT_HUB = "ARRIVED_AT_HUB";
    public static final String READY_FOR_DELIVERY = "READY_FOR_DELIVERY";
    public static final String DELIVERING_COMPLETED = "DELIVERING_COMPLETED";

  }

}

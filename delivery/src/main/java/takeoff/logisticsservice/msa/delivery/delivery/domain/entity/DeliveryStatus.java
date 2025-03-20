package takeoff.logisticsservice.msa.delivery.delivery.domain.entity;

public enum DeliveryStatus {
  ORDERED(Status.ORDERED),
  DELIVERING(Status.DELIVERING),
  COMPLETED(Status.COMPLETED);

  private final String status;

  public String getStatus() {
    return this.status;
  }

  DeliveryStatus(String status) {
    this.status = status;
  }

  public static class Status {

    public static final String ORDERED = "ORDERED";
    public static final String DELIVERING = "DELIVERING";
    public static final String COMPLETED = "COMPLETED";
  }
}

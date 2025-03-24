package takeoff.logisticsservice.msa.delivery.delivery.application.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.BusinessException;

@Getter
public class DeliveryBusinessException extends BusinessException {

  private DeliveryBusinessException(DeliveryErrorCode errorCode) {
    super(errorCode);
  }

}

package takeoff.logistics_service.msa.order.application.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.BusinessException;

@Getter
public class OrderBusinessException extends BusinessException {

  private OrderBusinessException(OrderErrorCode errorCode) {
    super(errorCode);
  }

}

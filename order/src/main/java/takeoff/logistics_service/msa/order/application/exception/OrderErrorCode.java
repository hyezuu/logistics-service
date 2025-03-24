package takeoff.logistics_service.msa.order.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
  ORDER_NOT_FOUND("ORDER_001", "존재하지 않는 주문 정보입니다.", 404);

  private final String code;
  private final String message;
  private final int status;
}

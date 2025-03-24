package takeoff.logisticsservice.msa.delivery.delivery.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum DeliveryErrorCode implements ErrorCode {
  DELIVERY_NOT_FOUND("DELIVERY_001", "존재하지 않는 배송 정보입니다.",
      HttpStatus.NOT_FOUND.value()),
  INVALID_DELIVERY_STATUS("DELIVERY_002", "유효하지 않은 배송 상태입니다.",
      HttpStatus.BAD_REQUEST.value());

  private final String code;
  private final String message;
  private final int status;
}

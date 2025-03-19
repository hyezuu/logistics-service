package takeoff.logistics_service.msa.product.stock.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum StockErrorCode implements ErrorCode {

	INVALID_REQUEST("STK_001", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
	STOCK_NOT_FOUND("STK_004", "요청하신 재고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	STOCK_LOCK_TIMEOUT("STK_005", "재고 처리 중 락 타임아웃이 발생했습니다.", HttpStatus.CONFLICT);

	private final String code;
	private final String message;
	private final int status;

	StockErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}

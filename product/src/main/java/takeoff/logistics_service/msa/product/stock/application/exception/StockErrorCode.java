package takeoff.logistics_service.msa.product.stock.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum StockErrorCode implements ErrorCode {

	INVALID_REQUEST("STK_001", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
	INVALID_USER_REQUEST("STK_002", "잘못된 유저 요청입니다.", HttpStatus.BAD_REQUEST),
	DUPLICATE_STOCK_ID("STK_003", "해당 상품이 이미 존재합니다.", HttpStatus.CONFLICT),
	STOCK_NOT_FOUND("STK_004", "요청하신 재고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	STOCK_LOCK_TIMEOUT("STK_005", "재고 처리 중 락 타임아웃이 발생했습니다.", HttpStatus.CONFLICT),
	ACCESS_DENIED("STK_010", "해당 리소스에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
	UNAUTHORIZED_ACCESS("STK_011", "인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
	USER_NOT_FOUND("STK_012", "해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final int status;

	StockErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}

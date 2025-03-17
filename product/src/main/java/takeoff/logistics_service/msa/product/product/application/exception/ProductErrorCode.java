package takeoff.logistics_service.msa.product.product.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum ProductErrorCode implements ErrorCode {
	INVALID_STOCK_REQUEST("PRD_001", "재고 요청이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
	STOCK_NOT_FOUND("PRD_004", "요청한 재고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	STOCK_CONFLICT("PRD_005", "재고 처리 중 충돌이 발생했습니다.", HttpStatus.CONFLICT),
	PRODUCT_NOT_FOUND("PRD_006", "요청한 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final int status;

	ProductErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}
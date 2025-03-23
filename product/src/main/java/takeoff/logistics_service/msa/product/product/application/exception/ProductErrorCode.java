package takeoff.logistics_service.msa.product.product.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum ProductErrorCode implements ErrorCode {
	INVALID_STOCK_REQUEST("PRD_001", "재고 요청이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
	INVALID_HUB_REQUEST("PRD_002", "허브 요청이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
	INVALID_COMPANY_REQUEST("PRD_003", "업체 요청이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
	STOCK_NOT_FOUND("PRD_004", "요청한 재고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	STOCK_CONFLICT("PRD_005", "재고 처리 중 충돌이 발생했습니다.", HttpStatus.CONFLICT),
	PRODUCT_NOT_FOUND("PRD_006", "요청한 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	HUB_NOT_FOUND("PRD_007", "해당 허브를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	COMPANY_NOT_FOUND("PRD_008", "해당 업체를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	INVALID_USER_REQUEST("PRD_009", "잘못된 유저 요청입니다.", HttpStatus.BAD_REQUEST),
	ACCESS_DENIED("PRD_010", "해당 리소스에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
	UNAUTHORIZED_ACCESS("PRD_011", "인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
	USER_NOT_FOUND("PRD_012", "해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	PRODUCT_SAVE_FAILED("PRD_013", "상품 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	STOCK_CLIENT_TIMEOUT("PRD_014", "재고 서비스 호출에 타임아웃이 발생했습니다.", HttpStatus.GATEWAY_TIMEOUT);

	private final String code;
	private final String message;
	private final int status;

	ProductErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}
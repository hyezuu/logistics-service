package takeoff.logistics_service.msa.common.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements ErrorCode {

	INVALID_REQUEST("COM_001", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED("COM_002", "인증되지 않은 요청입니다.", HttpStatus.UNAUTHORIZED),
	FORBIDDEN("COM_003", "접근이 금지 되었습니다.", HttpStatus.FORBIDDEN),
	NOT_FOUND("COM_004", "요청한 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	INTERNAL_SERVER_ERROR("COM_005", "서버 에러 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_IMPLEMENTED("COM_006", "요청한 URI 페이지는 없습니다.", HttpStatus.NOT_IMPLEMENTED),
	BAD_GATEWAY("COM_007", "서버 간 통신이 올바르지 않습니다.", HttpStatus.BAD_GATEWAY),
	VALIDATION_ERROR("COM_008", "입력값 검증에 실패했습니다.", HttpStatus.BAD_REQUEST),
	CONSTRAINT_VIOLATION("COM_009", "제약조건 위반이 발생했습니다.", HttpStatus.BAD_REQUEST),
	METHOD_NOT_ALLOWED("COM_010", "지원하지 않는 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED);

	private final String code;
	private final String message;
	private final int status;

	CommonErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}

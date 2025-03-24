package takeoff.logistics_service.msa.company.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum CompanyErrorCode implements ErrorCode {
	COMPANY_NOT_FOUND("CPN_004", "해당 업체를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	COMPANY_NAME_CONFLICT("CPN_005", "중복된 업체명입니다.", HttpStatus.CONFLICT),
	HUB_NOT_FOUND("CPN_006", "존재하지 않는 허브입니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final int status;

	CompanyErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}
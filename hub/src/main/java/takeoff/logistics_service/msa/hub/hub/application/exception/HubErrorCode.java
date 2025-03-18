package takeoff.logistics_service.msa.hub.hub.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum HubErrorCode implements ErrorCode {

	HUB_NOT_FOUND("HUB_004", "존재하지 않는 허브입니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final int status;

	HubErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}

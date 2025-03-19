package takeoff.logistics_service.msa.hub.hubroute.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum HubRouteErrorCode implements ErrorCode {

	INVALID_HUB_ROUTE_REQUEST("HUB_ROUTE_001", "요청한 허브 ID가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
	NAVER_ERROR("HUB_ROUTE_005", "NAVER API 답을 받아 올 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	HUB_ROUTE_NOT_FOUND("HUB_ROUTE_004", "존재하지 않는 허브 경로 입니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final int status;

	HubRouteErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}

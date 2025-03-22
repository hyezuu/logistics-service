package takeoff.logistics_service.msa.slack.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public enum SlackErrorCode implements ErrorCode {

	SLACK_NOT_FOUND("SLK_004", "슬랙 메세지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	SLACK_ERROR("SLK_005", "슬랙 서버에 메시지를 생성할 수 업습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	GEMINI_ERROR("GEM_005", "AI 메세지를 생성할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String code;
	private final String message;
	private final int status;

	SlackErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}

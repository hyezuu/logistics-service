package takeoff.logistics_service.msa.slack.application.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public class SlackBusinessException extends BusinessException {

	private SlackBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}
}

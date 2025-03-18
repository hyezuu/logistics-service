package takeoff.logistics_service.msa.hub.hub.application.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public class HubBusinessException extends BusinessException {

	private HubBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}
}

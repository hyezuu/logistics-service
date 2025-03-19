package takeoff.logistics_service.msa.hub.hubroute.application.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public class HubRouteBusinessException extends BusinessException {

	private HubRouteBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}
}

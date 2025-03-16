package takeoff.logistics_service.msa.common.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	public static BusinessException from(ErrorCode errorCode) {
		return new BusinessException(errorCode);
	}

	protected BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}

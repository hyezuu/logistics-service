package takeoff.logistics_service.msa.company.application.exception;

import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

public class CompanyBusinessException extends BusinessException {

	private CompanyBusinessException(
		ErrorCode errorCode) {
		super(errorCode);
	}
}

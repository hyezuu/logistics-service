package takeoff.logistics_service.msa.product.product.application.exception;

import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

public class ProductBusinessException extends BusinessException {

	private ProductBusinessException(
		ErrorCode errorCode) {
		super(errorCode);
	}
}

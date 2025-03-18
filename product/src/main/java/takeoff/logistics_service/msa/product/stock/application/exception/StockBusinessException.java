package takeoff.logistics_service.msa.product.stock.application.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

@Getter
public class StockBusinessException extends BusinessException {

	private StockBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}
}

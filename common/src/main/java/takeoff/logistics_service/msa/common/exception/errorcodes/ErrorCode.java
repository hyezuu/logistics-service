package takeoff.logistics_service.msa.common.exception.errorcodes;

public interface ErrorCode {

	String getCode();
	String getMessage();
	int getStatus();
}
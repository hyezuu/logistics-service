package takeoff.logistics_service.msa.common.exception.code;

public interface ErrorCode {

	String getCode();
	String getMessage();
	int getStatus();
}
package takeoff.logistics_service.msa.common.exception;

import static takeoff.logistics_service.msa.common.exception.code.CommonErrorCode.INTERNAL_SERVER_ERROR;
import static takeoff.logistics_service.msa.common.exception.code.CommonErrorCode.METHOD_NOT_ALLOWED;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
		return ResponseEntity.status(errorResponse.status()).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getBindingResult());
		return ResponseEntity.status(errorResponse.status()).body(errorResponse);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(
		ConstraintViolationException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getConstraintViolations());
		return ResponseEntity.status(errorResponse.status()).body(errorResponse);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error(e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(METHOD_NOT_ALLOWED);
		return ResponseEntity.status(errorResponse.status()).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error(e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(errorResponse.status()).body(errorResponse);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getMessage(), HttpStatus.CONFLICT.value());
		return ResponseEntity.status(errorResponse.status()).body(errorResponse);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(errorResponse.status()).body(errorResponse);
	}
}

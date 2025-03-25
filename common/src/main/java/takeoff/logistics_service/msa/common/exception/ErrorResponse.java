package takeoff.logistics_service.msa.common.exception;

import static takeoff.logistics_service.msa.common.exception.code.CommonErrorCode.CONSTRAINT_VIOLATION;
import static takeoff.logistics_service.msa.common.exception.code.CommonErrorCode.VALIDATION_ERROR;

import jakarta.validation.ConstraintViolation;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.validation.BindingResult;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

public record ErrorResponse(String code, String message, int status, List<ValidationError> errors) {

	static final String ILLEGAL_ERROR_CODE = "ILL_001";

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(
			errorCode.getCode(),
			errorCode.getMessage(),
			errorCode.getStatus(),
			Collections.emptyList()
		);
	}

	public static ErrorResponse of(String message, int status) {
		return new ErrorResponse(
			ILLEGAL_ERROR_CODE,
			message,
			status,
			Collections.emptyList()
		);
	}

	public static ErrorResponse of(BindingResult bindingResult) {
		return new ErrorResponse(
			VALIDATION_ERROR.getCode(),
			VALIDATION_ERROR.getMessage(),
			VALIDATION_ERROR.getStatus(),
			ValidationError.ofFieldErrors(bindingResult.getFieldErrors())
		);
	}

	public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
		return new ErrorResponse(
			CONSTRAINT_VIOLATION.getCode(),
			CONSTRAINT_VIOLATION.getMessage(),
			CONSTRAINT_VIOLATION.getStatus(),
			ValidationError.ofConstraintViolations(violations)
		);
	}

	public record ValidationError(
		String field,
		String rejectedValue,
		String reason
	) {

		private static List<ValidationError> ofFieldErrors(
			List<org.springframework.validation.FieldError> fieldErrors) {
			return fieldErrors.stream()
				.map(error -> new ValidationError(
					error.getField(),
					error.getRejectedValue() == null ?
						"" : String.valueOf(error.getRejectedValue()),
					error.getDefaultMessage()))
				.toList();
		}

		private static List<ValidationError> ofConstraintViolations(
			Set<ConstraintViolation<?>> constraintViolations) {
			return constraintViolations.stream()
				.map(violation -> new ValidationError(
					violation.getPropertyPath().toString(),
					violation.getInvalidValue() == null ?
						"" : String.valueOf(violation.getInvalidValue()),
					violation.getMessage()))
				.toList();
		}
	}
}
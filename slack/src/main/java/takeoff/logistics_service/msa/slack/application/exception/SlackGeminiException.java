package takeoff.logistics_service.msa.slack.application.exception;

import lombok.Getter;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.ErrorCode;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Getter
public class SlackGeminiException extends BusinessException {

    private SlackGeminiException(ErrorCode errorCode) {super(errorCode);
    }
}

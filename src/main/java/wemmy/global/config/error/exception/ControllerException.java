package wemmy.global.config.error.exception;

import lombok.Getter;
import wemmy.global.config.error.ErrorCode;

@Getter
public class ControllerException extends RuntimeException {

    private ErrorCode errorCode;

    public ControllerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}

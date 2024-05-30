package wemmy.global.config.error.exception;

import lombok.Getter;
import wemmy.global.config.error.ErrorCode;

@Getter
public class BabyException extends RuntimeException {

    private ErrorCode errorCode;

    public BabyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BabyException(String message, Throwable cause) {
        super(message, cause);
    }
}

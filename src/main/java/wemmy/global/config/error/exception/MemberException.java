package wemmy.global.config.error.exception;

import lombok.Getter;
import wemmy.global.config.error.ErrorCode;

@Getter
public class MemberException extends RuntimeException {

    private ErrorCode errorCode;

    public MemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }
}

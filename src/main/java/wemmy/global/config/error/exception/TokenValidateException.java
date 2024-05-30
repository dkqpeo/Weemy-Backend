package wemmy.global.config.error.exception;

import lombok.Getter;
import wemmy.global.config.error.ErrorCode;

@Getter
public class TokenValidateException extends RuntimeException{

    private ErrorCode errorCode;

    public TokenValidateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public TokenValidateException(String message, Throwable cause) {
        super(message, cause);

    }
}

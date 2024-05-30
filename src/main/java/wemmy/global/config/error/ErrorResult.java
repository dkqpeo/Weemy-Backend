package wemmy.global.config.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResult {

    private String errorCode;
    private String message;

    public static ErrorResult of (String errorCode, String message){
        return ErrorResult.builder()
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}

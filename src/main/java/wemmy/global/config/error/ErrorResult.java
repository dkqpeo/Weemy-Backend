package wemmy.global.config.error;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@Slf4j
public class ErrorResult {

    private String errorCode;
    private String message;

    public static ErrorResult of (String errorCode, String message){

        log.info("ErrorResult.message : " + message);

        return ErrorResult.builder()
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
